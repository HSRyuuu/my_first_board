package hello.board.web.board;

import hello.board.domain.member.Member;
import hello.board.domain.member.MemberRepository;
import hello.board.domain.post.Post;
import hello.board.domain.post.PostRepository;
import hello.board.web.board.form.SearchForm;
import hello.board.web.board.form.WritingForm;
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
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 게시판 메인화면 GET
     * show = true : 로그인시 - model에  loginMember를 담아서 넘긴다. - 멤버정보 메뉴, 로그아웃버튼 출력
     * show = false : 로그인 x 시 - 로그인 버튼 출력
     */
    @GetMapping
    public String boardHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            Model model){

        boolean show = true;
        if(loginMember == null){
            show = false;
        }else {
            model.addAttribute("member", loginMember);
        }
        model.addAttribute("show",show);
        model.addAttribute("form",new SearchForm());

        return "board/board";
    }
    @PostMapping()
    public String searchResult(@Validated @ModelAttribute("form") SearchForm form, BindingResult bindingResult,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                               RedirectAttributes redirectAttributes,
                               Model model){
        log.info("searchCode={}",form.getSearchCode());
        //빈칸일때는 다시 메인화면으로
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "redirect:/board";
        }
        //검색 로직
        String searchWord = form.getSearchWord();
        String searchCode = form.getSearchCode();
        List<Post> searchList = getSearchList(searchWord, searchCode);
        log.info("searchListSize={}",searchList.size());
        if(searchList.size()==0){
            log.info("{} : 검색결과없음",searchCode);
            bindingResult.reject("notFoundResult");
            if(loginMember == null){
                return "board/board";
            }else{
                model.addAttribute("show",true);
                model.addAttribute("member",loginMember);
                return "board/board";
            }
        }

        //성공 로직
        redirectAttributes.addAttribute("searchCode",form.getSearchCode());
        redirectAttributes.addAttribute("searchWord",form.getSearchWord());
        return "redirect:/board/{searchCode}/{searchWord}";
    }

    @GetMapping("/{searchCode}/{searchWord}")
    public String searchResult(@PathVariable String searchCode, @PathVariable String searchWord,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,Model model){
        List<Post> searchList = getSearchList(searchWord,searchCode);

        boolean show = true;
        if(loginMember == null){
            show = false;
        }else {
            model.addAttribute("member", loginMember);
        }
        model.addAttribute("show",show);
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
            log.info("error={}",bindingResult);
            return "redirect:/board";
        }
        //검색 값 받아서 해당 값에대한 글 리스트 생성
        String searchWord = form.getSearchWord();
        String searchCode = form.getSearchCode();
        List<Post> searchList = getSearchList(searchWord, searchCode);

        //리스트가 비어있을 때
        if(searchList.size()==0){
            log.info("{} : 검색결과없음",searchCode);
            bindingResult.reject("notFoundResult");
            if(loginMember == null){
                model.addAttribute("findPosts",getSearchList(lastSearchWord,lastSearchCode));
                return "board/findPosts";
            }else{
                model.addAttribute("show",true);
                model.addAttribute("member",loginMember);
                model.addAttribute("findPosts",getSearchList(lastSearchWord,lastSearchCode));
                return "board/findPosts";
            }
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

    public List<Post> getSearchList(String searchWord, String searchCode){
        List<Post> searchList;
        switch(searchCode){
            case "find-by-title" : {
                searchList = postRepository.findByTitle(searchWord);
            }break;
            case "find-by-content" :{
                searchList = postRepository.findByContents(searchWord);
            }break;
            case "find-by-writer-id" :{
                searchList = postRepository.findByWriterId(searchWord);
            }break;
            default: searchList = new ArrayList<>();
        }
        return searchList;

    }


    @ModelAttribute("searchCodes")
    public List<PostSearchCode> searchCodes(){
        List<PostSearchCode> searchCodes = new ArrayList<>();
        searchCodes.add(new PostSearchCode("find-by-title", "제목"));
        searchCodes.add(new PostSearchCode("find-by-content", "내용"));
        searchCodes.add(new PostSearchCode("find-by-writer-id", "작성자"));
        return searchCodes;
    }

    @ModelAttribute("posts")
    public List<Post> posts(){
        List<Post> list = postRepository.findAll();
        Collections.sort(list, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return (int)(o2.getId()-o1.getId());
            }
        });
        return list;
    }
}