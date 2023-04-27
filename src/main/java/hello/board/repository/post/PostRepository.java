package hello.board.repository.post;

import hello.board.domain.member.Member;
import hello.board.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post); //게시글 저장
    Optional<Post> findById(Long id); //게시글 시스템 id로 찾기
    List<Post> findByWriterId(String writerId); // 작성자 로그인id로 찾기
    List<Post> findByTitle(String title); //제목으로 찾기
    List<Post> findByContents(String content);//내용으로 찾기
    List<Post> findByTitleAndContent(String word);
    List<Post> findAll(); //모든 글 list를 return
    Post updatePost(Long id, Post updateParam);//멤버 수정
    void addView(Long postId);// 조회수1 증가
    void deletePost(Long id);

}
