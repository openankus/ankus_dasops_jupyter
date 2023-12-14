package org.ankus.service;

import org.ankus.model.*;
import org.ankus.repository.CodeSearchTagRepository;
import org.ankus.repository.OldShareCodeRepository;
import org.ankus.repository.ShareCodeRepository;
import org.ankus.repository.ShareCodeTagRepository;
import org.ankus.model.User;
import org.ankus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareCodeService {
    @Autowired
    private OldShareCodeRepository oldCodeRepo;
    @Autowired
    private ShareCodeRepository codeRepo;
    @Autowired
    private CodeSearchTagRepository searchTagRepo;
    @Autowired
    private ShareCodeTagRepository codeTagRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User login(String loginId, String password) {
        User user = userRepository.findUserByLoginId(loginId);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword()) )
                return user;
        }

        return null;
    }

    public Page<ShareCodeView> codeList(String searchCol, List<String> searchList, String orderCol, boolean asc, int pageNo) {
        PageRequest pr = PageRequest.of(pageNo, 5, asc?
                Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()));
        //Page<ShareCode> codelist = null;

        //whole
        if (searchList.isEmpty()) {
            return codeRepo.findAllWithTag(pr);
        }
        //title
        else if (searchCol.equals(CodeSearchOption.Name.getColumnName())) {
            return codeRepo.findAllByNameContains(searchList.get(0), pr);
        }
        //writer
        else if (searchCol.equals(CodeSearchOption.Writer.getColumnName())) {
            return codeRepo.findAllByWriterContains(searchList.get(0), pr);
        }
        //comment
        else if (searchCol.equals(CodeSearchOption.Comment.getColumnName())) {
            return codeRepo.findAllByCommentContains(searchList, pr);
        }
        //tag
        else if (searchCol.equals(CodeSearchOption.Tag.getColumnName())) {
            return codeRepo.findAllByTagContains(searchList, pr);
        }

        return null;//codelist.map(ShareCode::toDTO);
    }

    public Page<ShareCode> oldCodeList(String searchCol, List<String> searchList, String orderCol, boolean asc, int pageNo) {
        PageRequest pr = PageRequest.of(pageNo, 5, asc?
                Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()));
        //Page<ShareCode> codelist = null;

        //whole
        if (searchList.isEmpty()) {
            return oldCodeRepo.findAllWithTag(pr);
        }
        //title
        else if (searchCol.equals(CodeSearchOption.Name.getColumnName())) {
            return oldCodeRepo.findAllByNameContains(searchList.get(0), pr);
        }
        //writer
        else if (searchCol.equals(CodeSearchOption.Writer.getColumnName())) {
            return oldCodeRepo.findAllByWriterContains(searchList.get(0), pr);
        }
        //comment
        else if (searchCol.equals(CodeSearchOption.Comment.getColumnName())) {
            return oldCodeRepo.findAllByCommentContains(searchList, pr);
            //Specification.where(ShareCodeSpecifications.containComment(searchList)), pr);
        }
        //tag
        else if (searchCol.equals(CodeSearchOption.Tag.getColumnName())) {
            return oldCodeRepo.findAllByTagContains(searchList, pr);
        }

        return null;//codelist.map(ShareCode::toDTO);
    }
    public CodeDto addCode(CodeDto code) {
        //save code
        ShareCode co = codeRepo.save(code.toEntity());
        long cid = co.getCodeId();
        code.setCodeId(cid);
        code.setUdate(co.getUdate());

        List<TagDto> tags = code.getTags();
        if (tags != null)
            //save tag
            tags.forEach(tagDto -> {
                //tag 검색해서, 없으면 추가
                ShareCodeSearchTag to = searchTagRepo.findByName(tagDto.getName()).orElseGet(
                        ()->searchTagRepo.save(tagDto.toEntity()));
                //save code-tag
                codeTagRepo.save(
                        ShareCodeTag.builder().codeTagId(
                                CodeTagId.builder().cid(cid).tid(to.getTagId()).build())
                                .build());
            });

        return code;
    }//addCode

    public CodeDto updateCode(CodeDto code) {
        ShareCode sc = codeRepo.save(code.toEntity());
        code.setUdate(sc.getUdate());

        List<TagDto> newTag = code.getTags();
        if (newTag != null) {
            long cid = code.getCodeId();
            List<TagDto> oldTag = codeTagRepo.findTagByCodeId(cid);

            List<CodeTagId> delTag = new ArrayList<>();
            for (TagDto ot : oldTag) {
                if (newTag.stream().noneMatch(nt -> nt.getName().equals(ot.getName())))
                    delTag.add(CodeTagId.builder().cid(cid).tid(ot.getTagId()).build());
            }
            //delete code-tag
            codeTagRepo.deleteAllById(delTag);

            List<ShareCodeTag> addTag = new ArrayList<>();
            for (TagDto nt : newTag) {
                if (oldTag.stream().noneMatch(ot -> ot.getName().equals(nt.getName()))) {
                    //tag 검색해서, 없으면 추가
                    ShareCodeSearchTag to = searchTagRepo.findByName(nt.getName()).orElseGet(
                            () -> searchTagRepo.save(ShareCodeSearchTag.builder().name(nt.getName()).build()));
                    addTag.add(ShareCodeTag.builder().codeTagId(
                            CodeTagId.builder().cid(cid).tid(to.getTagId()).build()).build());
                }
            }
            //add code-tag
            codeTagRepo.saveAll(addTag);

            //사용하지 않는 tag 삭제
            searchTagRepo.deleteNotUsed();
        }

        return code;
    }

    public void deleteCodeTag(long cid, List<Integer> tid) {
        //delete code-tag map table
        codeTagRepo.deleteAllByIdInBatch(tid.stream().map(id ->
                CodeTagId.builder().cid(cid).tid(id).build()).collect(Collectors.toList()));

        //사용하지 않는 태그 삭제
        searchTagRepo.deleteNotUsed();
    }

    public void deleteCode(long cid) {
        codeTagRepo.deleteByCodeId(cid);
        //delete code
        codeRepo.deleteById(cid);

        //사용하지 않는 태그 삭제
        searchTagRepo.deleteNotUsed();
    }

    public List<TagDto> addCodeTag(long cid, List<String> tags) {
        List<TagDto> res = new ArrayList<>();

        //string list -> codetag list
        List taglist = tags.stream().map(tag -> {
            ShareCodeSearchTag to = searchTagRepo.findByName(tag).orElseGet(
                ()->searchTagRepo.save(ShareCodeSearchTag.builder().name(tag).build()));

            res.add(to.toDTO());

            return ShareCodeTag.builder().codeTagId(
                    CodeTagId.builder().cid(cid).tid(to.getTagId()).build()).build();
        }).collect(Collectors.toList());

        //save codetag
        codeTagRepo.saveAll(taglist);
        return res;
    }//addCodeTag

    public CodeDto codeDetail(long id) {
        return codeRepo.findById(id);
    }
}
