package hello.board.web;

import hello.board.domain.member.Member;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import hello.board.service.post.PostManager;
import hello.board.service.post.PostSearchCode;
import hello.board.web.form.board.SearchForm;
import hello.board.web.form.board.WritingForm;
import hello.board.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final PostRepository postRepository;
    private final PostManager postManager;

    /**
     * 게시판 메인화면 GET
     * show = true : 로그인 o - 멤버정보 메뉴, 로그아웃버튼 출력
     * show = false : 로그인 x - 로그인, 회원가입 버튼 출력
     */
    @GetMapping
    public String boardHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            Model model){
        model.addAttribute("show",isLoggedin(loginMember, model));
        model.addAttribute("form",new SearchForm());
        return "board/board";
    }

    /**
     * 게시판 메인화면에서 게시글 검색 POST
     */
    @PostMapping()
    public String postSearchResult(@Validated @ModelAttribute("form") SearchForm form, BindingResult bindingResult,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                               RedirectAttributes redirectAttributes,
                               Model model){
        log.info("PostMapping[searchResult] searchCode={}",form.getSearchCode());
        //빈칸일때는 다시 메인화면으로
        if(bindingResult.hasErrors()){
            return "redirect:/board";
        }
        List<Post> searchList = postManager.getSearchedList(form.getSearchCode(), form.getSearchWord());

        if(searchList.size()==0){
            bindingResult.reject("notFoundResult");
            isLoggedin(loginMember, model);
            return "board/board";
        }
        //성공 로직
        redirectAttributes.addAttribute("searchCode", form.getSearchCode());
        redirectAttributes.addAttribute("searchWord", form.getSearchWord());
        return "redirect:/board/{searchCode}/{searchWord}";
    }

    @GetMapping("/{searchCode}/{searchWord}")
    public String searchResult(@PathVariable String searchCode, @PathVariable String searchWord,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,Model model){
        List<Post> searchList = postManager.getSearchedList(searchCode, searchWord);

        isLoggedin(loginMember, model);
        model.addAttribute("form",new SearchForm());
        model.addAttribute("findPosts",searchList);
        return "board/findPosts";
    }

    /**
     * @param lastSearchCode : 경로에 포함된 searchCode
     * @param lastSearchWord : 경로에 포함된 searchWord
     * @param form : 해당 페이지에서 입력받은 form ( searchCode, searchWord 포함 )
     */
    @PostMapping("/{searchCode}/{searchWord}")
    public String searchResultFromFindList(@PathVariable("searchCode") String lastSearchCode, @PathVariable("searchWord") String lastSearchWord,
                                           @Validated @ModelAttribute("form") SearchForm form, BindingResult bindingResult,
                                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                                           RedirectAttributes redirectAttributes,
                                           Model model){
        //빈칸일때는 다시 메인화면으로
        if(bindingResult.hasErrors()){
            return "redirect:/board";
        }
        //검색 값 받아서 해당 값에대한 글 리스트 생성
        List<Post> searchList = postManager.getSearchedList(form.getSearchCode(), form.getSearchWord());

        //리스트가 비어있을 때
        if(searchList.size()==0){
            bindingResult.reject("notFoundResult");
            model.addAttribute("findPosts", postManager.getSearchedList(lastSearchCode, lastSearchWord));
            isLoggedin(loginMember, model);
            return "board/findPosts";
        }

        //성공 로직
        redirectAttributes.addAttribute("searchCode",form.getSearchCode());
        redirectAttributes.addAttribute("searchWord",form.getSearchWord());
        return "redirect:/board/{searchCode}/{searchWord}";
    }


    /**
     * 글쓰기 폼 GET
     */
    @GetMapping("/write-form")
    public String writeForm(Model model){
        model.addAttribute("form", new WritingForm());
        return "post/writeForm";
    }

    /**
     * 세션에 존재하는 loginMember를 작성자로 등록
     */
    @PostMapping("/write-form")
    public String addWriting(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             @Validated @ModelAttribute("form")WritingForm form, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "post/writeForm";
        }

        //작성자를 세션에서 꺼내와서 등록
        Post post = new Post(loginMember.getLoginId(), form.getTitle(),form.getContent());
        Post savedPost = postRepository.save(post);

        redirectAttributes.addAttribute("postId",savedPost.getId());
        return "redirect:/board/post/{postId}";
    }

    /**
     * 로그인 체크 메소드
     * loginMember o : model에 loginMember와 show=true를 담음
     * loginMember x : show=false를 담음
     * @return Boolean show
     */
    private boolean isLoggedin(Member loginMember, Model model) {
        boolean show = true;

        if(loginMember == null)show = false;
        else model.addAttribute("member", loginMember);

        model.addAttribute("show",show);
        return show;
    }

    @ModelAttribute("searchCodes")
    public List<PostSearchCode> searchCodes(){
        return postManager.getSearchCodes();
    }

    @ModelAttribute("posts")
    public List<Post> posts(){
        List<Post> list = postRepository.findAll();
        return postManager.sortByLatest(list);
    }


}