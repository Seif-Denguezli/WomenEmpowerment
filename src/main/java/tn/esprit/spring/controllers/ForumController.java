package tn.esprit.spring.controllers;


import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.service.forum.*;



@RestController
public class ForumController {
	
	@Autowired
	ForumService forumService ;
	
	
	@PostMapping("/add-Post/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> addPost_affectedto_User(@RequestBody Post post, @PathVariable("IdUser") Long IdUser) {
		return forumService.addPost(post,IdUser);
	}
	
	@PostMapping("/add-Com-to-Com/{IdCom}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> add_Com_to_Com(@RequestBody PostComment post, @PathVariable("IdUser") Long IdUser, @PathVariable("IdCom") Long IdCom) {
		return forumService.add_Com_to_Com(post,IdUser,IdCom);
	}
	
	@PostMapping("/add-Comment/{IdPost}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> addComment_to_Post(@RequestBody PostComment postComment, @PathVariable("IdPost") Long IdPost, @PathVariable("IdUser") Long IdUser) {
		return forumService.addComment_to_Post(postComment,IdPost,IdUser);
	}
	
	@PostMapping("/add-Like-post/{IdPost}/{IdUser}")
	@ResponseBody
	public PostLike addLike_to_Post(@RequestBody PostLike postLike, @PathVariable("IdPost") Long IdPost, @PathVariable("IdUser") Long IdUser) {
		return forumService.addLike_to_Post(postLike,IdPost,IdUser);
	}
	/*
	@PostMapping("/add-DisLike-post/{IdPost}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> addDisLike_to_Post(@RequestBody PostDislike postDisLike, @PathVariable("IdPost") Long IdPost, @PathVariable("IdUser") Long IdUser) {
		return forumService.addDisLike_to_Post(postDisLike,IdPost,IdUser);
	}
	@DeleteMapping("/Delete-DisLike/{IdDisLike}/{IdUser}")
	public ResponseEntity<?> Delete_DisLike( @PathVariable("IdDisLike") Long IdDisLike, @PathVariable("IdUser") Long IdUser) {
		return forumService.Delete_DisLike(IdDisLike,IdUser);
	}
	*/
	@PostMapping("/add-Like-Comment/{IdComment}/{IdUser}")
	@ResponseBody
	public CommentLike addLike_to_Comment(@RequestBody CommentLike commentLike, @PathVariable("IdComment") Long IdComment, @PathVariable("IdUser") Long IdUser) {
		return forumService.addLike_to_Comment(commentLike,IdComment,IdUser);
	}
	
	
	@PutMapping("/Update-Post/{IdPost}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> Update_Post(@RequestBody Post post, @PathVariable("IdPost") Long IdPost, @PathVariable("IdUser") Long IdUser) {
		return forumService.Update_post(post,IdPost,IdUser);
	}
	
	
	@PutMapping("/Update-Comment/{IdPostCom}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> Update_Comment(@RequestBody PostComment postComment, @PathVariable("IdPostCom") Long IdPostCom, @PathVariable("IdUser") Long IdUser) {
		return forumService.Update_Comment(postComment,IdPostCom,IdUser);
	}
	
	@PutMapping("/Update-Likes-Dislikes/{IdLike}")
	@ResponseBody
	public ResponseEntity<?> Swap_like_dislike( @PathVariable("IdLike") Long IdLike) {
		return forumService.Swap_like_dislike(IdLike);
	}
	
	 
	
	@GetMapping("/Get-all-Post")
	public Set<Post> Get_all_post(){
		return forumService.Get_all_post();
	}
	
	@GetMapping("/Get-Posts-By-user/{IdUser}")
	public Set<Post> Get_post_by_User( @PathVariable("IdUser") Long IdUser){
		return forumService.Get_post_by_User(IdUser);
	}
	
	@DeleteMapping("/Delete-Like/{IdLike}/{IdUser}")
	public ResponseEntity<?> Delete_Like( @PathVariable("IdLike") Long IdLike, @PathVariable("IdUser") Long IdUser) {
		return forumService.Delete_Like(IdLike,IdUser);
	}
	
	
	@GetMapping("/Get-post-Likes/{IdPost}")
	public Set<PostLike> Get_post_Likes( @PathVariable("IdPost") Long IdPost){
		return forumService.Get_post_Likes(IdPost);
	}
	

	@GetMapping("/Get-post-DisLikes/{IdPost}")
	public Set<PostLike> Get_post_DisLikes( @PathVariable("IdPost") Long IdPost){
		return forumService.Get_post_DisLikes(IdPost);
	}
	
	
	@DeleteMapping("/Delete-Post/{IdPost}/{IdUser}")
	public ResponseEntity<?> Delete_Post( @PathVariable("IdPost") Long IdPost, @PathVariable("IdUser") Long IdUser) {
		return forumService.Delete_post(IdPost,IdUser);
	}
	
	@DeleteMapping("/Delete-PostComment/{IdPostCom}/{IdUser}")
	public ResponseEntity<?> Delete_PostCom( @PathVariable("IdPostCom") Long IdPostCom, @PathVariable("IdUser") Long IdUser) {
		return forumService.Delete_PostCom(IdPostCom,IdUser);
	}
}

