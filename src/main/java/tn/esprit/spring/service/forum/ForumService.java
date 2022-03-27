package tn.esprit.spring.service.forum;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.*;

import tn.esprit.spring.repository.*;

@Service
public class ForumService {

	
	@Autowired
	AdvertisingRepo advertisingRepo;
	
	@Autowired
	PostRepo postRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	PostCommentRepo postCommentRepo;

	@Autowired
	PostLikeRepo postLikeRepo;

	@Autowired
	PostDislikeRepo postDislikeRepo;

	@Autowired
	CommentLikeRepo commentLikeRepo;

	@Autowired
	BadWordRepo badWordRepo;

	public ResponseEntity<?> addPost(Post post, Long IdUser) {

		User u = userRepo.findById(IdUser).orElse(null);

		if (Filtrage_bad_word(post.getBody()) == 0) {
			post.setUser(u);
			u.getPosts().add(post);
			postRepo.save(post);
			return ResponseEntity.ok().body(post);
		} else
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Bads Word Detected");
	}
	
	public ResponseEntity<?> addAdvertising(Advertising a, Long IdUser) {

		User u = userRepo.findById(IdUser).orElse(null);

		if (Filtrage_bad_word(a.getName()) == 0) {
			a.setUser(u);
			
			advertisingRepo.save(a);
			return ResponseEntity.ok().body(a);
		} else
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Bads Word Detected");
	}
	

	public ResponseEntity<?> addComment_to_Post(PostComment postComment, Long idPost, Long idUser) {
		Post p = postRepo.findById(idPost).orElse(null);
		User u = userRepo.findById(idUser).orElse(null);
		if (Filtrage_bad_word(postComment.getCommentBody()) == 0) {
			postComment.setUser(u);
			postComment.setPost(p);

			postCommentRepo.save(postComment);
			return ResponseEntity.ok().body(postComment);
			/*
			 * Set<PostComment> pc = p.getPostComments(); pc.add(postComment);
			 * p.setPostComments(pc); postRepo.save(p);
			 * 
			 * Set<PostComment> pu = u.getPostComments(); pu.add(postComment);
			 * u.setPostComments(pu); userRepo.save(u);
			 * 
			 * 
			 */}
		return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Bads Word Detected");
	}

	public PostLike addLike_to_Post(PostLike postLike, Long idPost, Long idUser) {
		Post p = postRepo.findById(idPost).orElse(null);
		User u = userRepo.findById(idUser).orElse(null);

		postLike.setUser(u);
		postLike.setPost(p);
		return postLikeRepo.save(postLike);
	}

	/*
	 * public ResponseEntity<?> addDisLike_to_Post(PostDislike postDisLike, Long
	 * idPost, Long idUser) { Post p = postRepo.findById(idPost).orElse(null); User
	 * u = userRepo.findById(idUser).orElse(null);
	 * 
	 * Delete_Like(get_like_exist(idUser, idPost).getPostLikeId(), idUser);
	 * 
	 * postDisLike.setUser(u); postDisLike.setPost(p);
	 * postDislikeRepo.save(postDisLike);
	 * 
	 * return ResponseEntity.ok().body(get_like_exist(idUser,
	 * idPost).getPostLikeId()); }
	 */
	public CommentLike addLike_to_Comment(CommentLike commentLike, Long idComment, Long idUser) {
		User u = userRepo.findById(idUser).orElse(null);
		PostComment p = postCommentRepo.findById(idComment).orElse(null);

		commentLike.setUser(u);
		commentLike.setPostComment(p);
		return commentLikeRepo.save(commentLike);
	}

	public ResponseEntity<?> Update_post(Post post, Long idPost, Long idUser) {
		if (postRepo.existsById(idPost)) {
			Post post1 = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("post not found"));
			//User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			

				post1.setPostTitle(post.getPostTitle());
				post1.setBody(post.getBody());
				postRepo.saveAndFlush(post1);
				return ResponseEntity.ok().body(post);
			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post Not Founf");
		}
	}
	
	public ResponseEntity<?> Update_Adversting(Advertising a, Long idPost) {
		if (postRepo.existsById(idPost)) {
			Advertising a1 = advertisingRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("adversting not found"));
			//User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			

				a1.setName(a.getName());
				a1.setCanal(a.getCanal());
				a1.setPrice(a.getPrice());
				a1.setEndDate(a.getEndDate());
				a1.setStartDate(a.getStartDate());
				
				advertisingRepo.saveAndFlush(a1);
				return ResponseEntity.ok().body(a1);
			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("adversting Not Founf");
		}
	}

	public ResponseEntity<?> Update_Comment(PostComment postComment, Long idPostCom, Long idUser) {
		if (postCommentRepo.existsById(idPostCom)) {
			PostComment postCom1 = postCommentRepo.findById(idPostCom)
					.orElseThrow(() -> new EntityNotFoundException("Comment not found"));
			//User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			//if (postCom1.getUser().equals(user)) {

				postCom1.setCommentBody(postComment.getCommentBody());

				return ResponseEntity.ok().body(postCom1);
			//} else {
			//	return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this post ");
		//	}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Founf");
		}
	}

	public ResponseEntity<?> Delete_post(Long idPost, Long idUser) {
		if (postRepo.existsById(idPost)) {
			Post post1 = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("post not found"));
			User user = userRepo.findById(post1.getUser().getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
			//if (post1.getUser().equals(user)) {
			Set<Post> p = user.getPosts();
			p.remove(post1);
			user.setPosts(p);
			userRepo.save(user);
				postRepo.delete(post1);
				return ResponseEntity.ok().body("Delete success");
		//	} else {
			//	return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this post");
			//}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post Not Founf");
		}

	}
	
	public ResponseEntity<?> Delete_Adversting(Long idadv) {
		if (advertisingRepo.existsById(idadv)) {
			Advertising a1 = advertisingRepo.findById(idadv).orElseThrow(() -> new EntityNotFoundException("adv not found"));
			//User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
		
			advertisingRepo.delete(a1);
				return ResponseEntity.ok().body("Delete success");
			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("adv Not Founf");
		}

	}

	public List<Post> Get_all_post() {
		List<Post> findAll = postRepo.findAll();
		return findAll;

	}

	public Set<Post> Get_post_by_User(Long idUser) {

		User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));

		return user.getPosts();
	}

	public int Filtrage_bad_word(String ch) {
		int x = 0;
		List<BadWord> l1 = (List<BadWord>) badWordRepo.findAll();
		for (BadWord badWord : l1) {
			// if (badWord.getWord().contains(ch))
			if (ch.contains(badWord.getWord()) == true)
				x = 1;
		}
		return x;

	}

	public ResponseEntity<?> Delete_Like(Long idLike, Long idUser) {
		if (postLikeRepo.existsById(idLike)) {
			PostLike postLike = postLikeRepo.findById(idLike)
					.orElseThrow(() -> new EntityNotFoundException("Like Not Found"));
			User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			if (postLike.getUser().equals(user)) {
				postLikeRepo.delete(postLike);
				return ResponseEntity.ok().body("Delete success");
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this post");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Like Not Found");
		}
	}

	public ResponseEntity<?> Delete_DisLike(Long idDisLike, Long idUser) {
		if (postDislikeRepo.existsById(idDisLike)) {
			PostDislike postDisLike = postDislikeRepo.findById(idDisLike)
					.orElseThrow(() -> new EntityNotFoundException("Like Not Found"));
			User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			if (postDisLike.getUser().equals(user)) {
				postDislikeRepo.delete(postDisLike);
				return ResponseEntity.ok().body("Delete success");
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this post");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Like Not Found");
		}
	}

	public Set<PostLike> Get_post_Likes(Long idPost) {
		Post post1 = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("post not found"));
		Set<PostLike> pp = post1.getPostLikes();
		for (PostLike postLike : pp) {
			if (postLike.getIsLiked() == false) {
				pp.remove(postLike);
			}
		}
		return pp;
	}

	public Set<PostLike> Get_post_DisLikes(Long idPost) {
		Post post1 = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("post not found"));
		Set<PostLike> pp = post1.getPostLikes();
		for (PostLike postLike : pp) {
			if (postLike.getIsLiked() == true) {
				pp.remove(postLike);
			}
		}
		return pp;
	}

	public ResponseEntity<?> Delete_PostCom(Long idPostCom, Long idUser) {
		if (postCommentRepo.existsById(idPostCom)) {
			PostComment postCom1 = postCommentRepo.findById(idPostCom)
					.orElseThrow(() -> new EntityNotFoundException("post not found"));
			User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			if (postCom1.getUser().equals(user)) {
				postCommentRepo.delete(postCom1);
				return ResponseEntity.ok().body("Delete success");
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this post");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post Not Founf");
		}
	}

	public ResponseEntity<?> add_Com_to_Com(PostComment postComment, Long idUser, Long idCom) {
		PostComment p = postCommentRepo.findById(idCom).orElse(null);
		User u = userRepo.findById(idUser).orElse(null);
		if (Filtrage_bad_word(postComment.getCommentBody()) == 0) {
			postComment.setUser(u);
			postComment.setPost(p.getPost());
			p.getPostComments().add(postComment);

			postCommentRepo.save(postComment);
			return ResponseEntity.ok().body(postComment);
			/*
			 * Set<PostComment> pc = p.getPostComments(); pc.add(postComment);
			 * p.setPostComments(pc); postRepo.save(p);
			 * 
			 * Set<PostComment> pu = u.getPostComments(); pu.add(postComment);
			 * u.setPostComments(pu); userRepo.save(u);
			 * 
			 * 
			 */
		}
		return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Bads Word Detected");
	}

	public ResponseEntity<?> Swap_like_dislike(Long idLike) {
		PostLike p = postLikeRepo.findById(idLike).orElse(null);
		p.setIsLiked(!p.getIsLiked());
		postLikeRepo.saveAndFlush(p);
		return ResponseEntity.ok().body(p);
	}
	@Scheduled(cron = "*/30 * * * * *")
	public void delete_sujet_sans_Int() {
		for (Post p : postRepo.findAll()) {
			if (date_comp(p.getCreatedAt())) {
				if (p.getPostLikes().size() == 0) {
					Delete_post(p.getPostId(), p.getUser().getUserId());

				}
			}
		}

	}

	public boolean date_comp(Date d) {
		if (LocalDate.now().getMonthValue() - d.getMonth() > 2) {
			return true;
		}
		if (LocalDate.now().getMonthValue() - d.getMonth() > 1) {
			if (LocalDate.now().getDayOfMonth() >= d.getDate()) {
				return true;
			}

			else {
				if (LocalDate.now().getDayOfMonth() - d.getDate() == 30) {
					return true;
				}
			}

		}

		return false;
	}

	public Post Get_best_Post() {
		Post p1 = null;
		int x = 0;
		for (Post p : postRepo.findAll()) {
			if (postRepo.diffrence_entre_date(p.getCreatedAt()) <= 7) {
				if (p.getPostLikes().size() > x) {
					p1 = p;
					x = p.getPostLikes().size();
				}
				/*
				 * else if (p.getPostLikes().size() == x) { if
				 * (postRepo.diffrence_entre_date(p.getCreatedAt())<postRepo.
				 * diffrence_entre_date(p1.getCreatedAt())) { p1 = p;} }
				 */
			}
		}
		return p1;
	}

	public Set<PostComment> Get_post_Comm(Long idPost) {
		Post p = postRepo.findById(idPost).orElse(null);
		return p.getPostComments();
	}

	public Set<PostComment> Get_comm_Comm(Long idComment) {
		PostComment p = postCommentRepo.findById(idComment).orElse(null);
		return p.getPostComments();
	}

	public Post Give_Etoile_Post(Long idPost, int nb_etouile) {
		Post post1 = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("post not found"));

		post1.setNb_etoil(nb_etouile);
		return postRepo.save(post1);

	}

	public Post Report_User(Long idPost) {
		Post post1 = postRepo.findById(idPost).orElseThrow(() -> new EntityNotFoundException("post not found"));

		post1.setNb_Signal(post1.getNb_Signal() + 1);
		return postRepo.save(post1);
	}
	
	//@Scheduled(cron = "*/30 * * * * *")
	public void delete_reported_post () {
		for (Post p : postRepo.findAll()) {
			if (p.getNb_Signal() >= 9) {
				Delete_post(p.getPostId(), p.getUser().getUserId());
			}
			
		}
	}

	public Set<Object> Get_more_likers_user() {
		return postLikeRepo.USer_order_by_Like();
		
	}


	public List<Message> get_conversation(Long idSender, Long idRecever) {
		
		return null;
	}
}
