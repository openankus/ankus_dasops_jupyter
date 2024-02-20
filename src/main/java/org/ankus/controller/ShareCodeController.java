package org.ankus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ankus.service.ShareCodeService;
import org.ankus.model.*;
import org.ankus.model.Role;
import org.ankus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.*;

@CrossOrigin(origins = "*")//{"http://localhost:8889", "http://192.168.2.109:8888"})
@Controller
public class ShareCodeController {
    @Autowired
    private ShareCodeService codeService;

    @PostMapping(value = "share-code/login")
    public ResponseEntity login(@RequestBody Map<String, String> info, HttpSession httpSession) {
        //login
        User usr = codeService.login(info.get("id"), info.get("password"));
        if (usr == null)
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);

        //token
        String tkn = JwtTokenProvider.createToken(usr.getLoginId(), String.valueOf(usr.getId()) );

        Map map = new HashMap();
        map.put("token", tkn);  //token
        map.put("name", usr.getName()); //user name
        map.put("id", usr.getId()); //user id

        boolean admin = false;
        //usr.getRoleList().stream().map(role -> role.getName()).collect(Collectors.toList());
        for (Role role : usr.getRoleList()) {
            if (role.getName().equals("관리자")) {
                admin = true;
                break;
            }
        }
        map.put("admin", admin);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity(map, header, HttpStatus.OK);
    }//login

    @PostMapping(value = "share-code/list")
    public ResponseEntity oldCodelist(@RequestBody Map<String, Object> option, HttpSession httpSession) {
        if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Slice<ShareCode> slice = codeService.oldCodeList(
                (String) option.get("searchColumn"), (List<String>)option.get("searchKeyword"),
                (String) option.get("orderColumn"), ((String) option.get("order")).equals("asc"),
                (Integer) option.get("page"));

        return new ResponseEntity<>(slice, header, HttpStatus.OK);
    }//codelist

    @PostMapping(value = "share-code/codelist")
    public ResponseEntity codelist(@RequestBody Map<String, Object> option, HttpSession httpSession) {
        if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Slice<ShareCodeView> slice = codeService.codeList(
                (String) option.get("searchColumn"), (List<String>)option.get("searchKeyword"),
                (String) option.get("orderColumn"), ((String) option.get("order")).equals("asc"),
                (Integer) option.get("page"));

        return new ResponseEntity<>(slice, header, HttpStatus.OK);
    }//codelist

    @PostMapping(value = "share-code/add")
    public ResponseEntity addCode(@RequestBody Map<String, Object> body, HttpSession httpSession) {
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");   //code object
        CodeDto code = new CodeDto();

            code.setContent((String) map.get("content"));
        code.setCodeId(0L);
        code.setUdate(new Date());
        code.setCodeComment((String) map.get("codeComment"));   //comment
        code.setTitle((String) map.get("title"));   //title
        code.setWriter(Long.valueOf(id));   //user id

        //tag list
        List list = new ArrayList();
        List<String> tags = (List) map.get("tags");
        tags.forEach(tag -> list.add(new TagDto(tag)));
        code.setTags(list);

        code = codeService.addCode(code);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(code, header, HttpStatus.OK);
    }//addCode

    @PostMapping(value = "share-code/new")
    public ResponseEntity newCode(@RequestBody Map<String, Object> body, HttpSession httpSession) {
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");   //code object
        CodeDto code = new CodeDto();

            ArrayList<String> al = (ArrayList<String>) map.get("content");
            //ipynb 형식으로 저장 : {cells:[]}
            Map cmap = new HashMap();
            cmap.put("cells", al);
            try {
                code.setContent(new ObjectMapper().writeValueAsString(cmap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        code.setCodeId(0L);
        code.setUdate(new Date());
        code.setCodeComment((String) map.get("codeComment"));   //comment
        code.setTitle((String) map.get("title"));   //title
        code.setWriter(Long.valueOf(id));   //user id

        //tag list
        List list = new ArrayList();
        List<String> tags = (List) map.get("tags");
        tags.forEach(tag -> list.add(new TagDto(tag)));
        code.setTags(list);

        code = codeService.addCode(code);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(code, header, HttpStatus.OK);
    }//addCode

    @PostMapping(value = "share-code/delete-tag/{code_id}")
    public ResponseEntity deleteTag(@PathVariable long code_id, @RequestBody Map<String, Object> body, HttpSession httpSession){
        if (JwtTokenProvider.validateToken((String) body.get("token")) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        codeService.deleteCodeTag(code_id, (List<Integer>) body.get("tags"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "share-code/add-tag/{code_id}")
    public ResponseEntity addTag(@PathVariable long code_id, @RequestBody Map<String, Object> body, HttpSession httpSession){
        if (JwtTokenProvider.validateToken((String) body.get("token")) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        List res = codeService.addCodeTag(code_id, (List<String>) body.get("tags"));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(res, header, HttpStatus.OK);
    }

    @PostMapping(value = "share-code/update")
    public ResponseEntity updateCode(@RequestBody Map<String, Object> body, HttpSession httpSession){
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");
        CodeDto code = new CodeDto();

            code.setContent((String) map.get("content"));
        code.setCodeId(Long.valueOf((Integer) map.get("codeId")));
        code.setCodeComment((String) map.get("codeComment"));   //comment
        code.setTitle((String) map.get("title"));   //title
        code.setWriter(Long.valueOf(id));   //user id

        code = codeService.updateCode(code);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(code, header, HttpStatus.OK);
    }

    @PutMapping(value = "share-code/modify")
    public ResponseEntity modifyCode(@RequestBody Map<String, Object> body, HttpSession httpSession){
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");
        CodeDto code = new CodeDto();

            ArrayList<String> al = (ArrayList<String>) map.get("content");
            //ipynb 형식으로 저장 : {cells:[]}
            Map cmap = new HashMap();
            cmap.put("cells", al);
            try {
                code.setContent(new ObjectMapper().writeValueAsString(cmap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        code.setCodeId(Long.valueOf((Integer) map.get("codeId")));
        code.setCodeComment((String) map.get("codeComment"));   //comment
        code.setTitle((String) map.get("title"));   //title
        code.setWriter(Long.valueOf(id));   //user id

        //tag list
        List list = new ArrayList();
        List<String> tags = (List) map.get("tags");
        tags.forEach(tag -> list.add(new TagDto(tag)));
        code.setTags(list);

        code = codeService.updateCode(code);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(code, header, HttpStatus.OK);
    }

    //ver1.1.1
    @PutMapping(value = "share-code/modify/prop")
    public ResponseEntity modifyProp(@RequestBody Map<String, Object> body, HttpSession httpSession){
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");
        CodeDto dto = codeService.codeDetail(Long.valueOf((Integer) map.get("codeId")));
        //description
        dto.setCodeComment((String) map.get("codeComment"));   //comment

        //tag list
        List list = new ArrayList();
        List<String> tags = (List) map.get("tags");
        tags.forEach(tag -> list.add(new TagDto(tag)));
        dto.setTags(list);

        codeService.updateCode(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }//modifyProp

    //ver1.1.1
    @PutMapping(value = "share-code/modify/code")
    public ResponseEntity modifyCodeContent(@RequestBody Map<String, Object> body, HttpSession httpSession){
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");

        ArrayList<String> al = (ArrayList<String>) map.get("content");
        //check code content
        if (al != null) {
            //ipynb 형식으로 저장 : {cells:[]}
            Map cmap = new HashMap();
            cmap.put("cells", al);

            try {
                //code.setCodeId(Long.valueOf((Integer) map.get("codeId")));
                //update code
                CodeDto code = codeService.updateCodeContent(Long.valueOf((Integer) map.get("codeId")), new ObjectMapper().writeValueAsString(cmap));

                //response
                HttpHeaders header = new HttpHeaders();
                header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
                return new ResponseEntity<>(code, header, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } //if : check code content
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }//modifyCodeContent

    //ver1.1.1
    @PutMapping(value = "share-code/modify/name")
    public ResponseEntity modifyCodeName(@RequestBody Map<String, Object> body, HttpSession httpSession){
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");
        CodeDto dto = codeService.codeDetail(Long.valueOf((Integer) map.get("codeId")));
        dto.setTitle((String) map.get("title"));
        codeService.updateCode(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }//modifyCodeContent

    //ver1.1.1
    @PostMapping(value = "share-code/duplicate")
    public ResponseEntity duplicateCode(@RequestBody Map<String, Object> body, HttpSession httpSession){
        //token -> user id
        String id = JwtTokenProvider.validateToken((String) body.get("token"));
        if (id == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        Map map = (Map) body.get("code");
        CodeDto dto = codeService.codeDetail((Integer) map.get("codeId"));
        //init id
        dto.setCodeId(0L);
        //writer no
        dto.setWriter(Long.valueOf((Integer) map.get("writer")));
        //save
        codeService.addCode(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }//modifyCodeContent

    @PostMapping(value = "share-code/{code_id}")
    public ResponseEntity codeDetail(@PathVariable long code_id, @RequestBody Map<String, String> body, HttpSession httpSession){
        if (JwtTokenProvider.validateToken(body.get("token")) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        CodeDto code = codeService.codeDetail(code_id);
        if (code == null) {
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            return new ResponseEntity(code, header, HttpStatus.OK);
        }
    }

    @GetMapping(value = "share-code/view")
    public ResponseEntity codeView(@RequestParam String token, @RequestParam long codeId, HttpSession httpSession){
        //check token
        if (JwtTokenProvider.validateToken(token) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        CodeDto code = codeService.codeDetail(codeId);  //code
        if (code == null) {
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Map res = new HashMap<String, Object>();
            res.put("title", code.getTitle());  //title
            res.put("codeComment", code.getCodeComment());  //comment
            res.put("writer", code.getWriter());//writer no
            res.put("udate", code.getUdate());  //udate
            res.put("name", code.getName());    //writer name
            res.put("tags", code.getTags());    //tag

            try {
                Map ary = new ObjectMapper().readValue(code.getContent(), Map.class);
                res.put("content", ary.get("cells"));
            } catch (Exception e) {
                //전체 텍스트 불러오기
                Map m = new HashMap<String,String>();
                m.put("source", code.getContent());
                m.put("cell_type", "code");

                Map[] ms = new Map[1];
                ms[0] = m;
                res.put("content", ms);
            }

            return new ResponseEntity(res, header, HttpStatus.OK);
        }//else : 코드 내용 전송
    }//codeView

    @PostMapping(value = "share-code/delete/{code_id}")
    public ResponseEntity deleteCode(@PathVariable long code_id, @RequestBody Map<String, String> body, HttpSession httpSession){
        if (JwtTokenProvider.validateToken(body.get("token")) == null)
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);

        codeService.deleteCode(code_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
