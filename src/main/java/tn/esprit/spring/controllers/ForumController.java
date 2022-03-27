package tn.esprit.spring.controllers;



import java.util.List;
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


import tn.esprit.spring.security.UserPrincipal;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.service.forum.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.security.core.annotation.AuthenticationPrincipal;



@RestController
public class ForumController {
	
	@Autowired
	ForumService forumService ;
	
	
	@PostMapping("/add-Post/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> addPost_affectedto_User(@RequestBody Post post,@ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		Long iduser = u.getId();
		return forumService.addPost(post,iduser);
	}
	
	@PostMapping("/add-Advertising/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> addAdvertising_affectedto_User(@RequestBody Advertising a,@ApiIgnore @AuthenticationPrincipal UserPrincipal u,Long idCategory) {
		return forumService.addAdvertising(a,u.getId(),idCategory);
	}
	
	@PostMapping("/add-Com-to-Com/{IdCom}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> add_Com_to_Com(@RequestBody PostComment post, @ApiIgnore @AuthenticationPrincipal UserPrincipal u, @PathVariable("IdCom") Long IdCom) {
		return forumService.add_Com_to_Com(post,u.getId(),IdCom);
	}
	
	@PostMapping("/add-Comment/{IdPost}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> addComment_to_Post(@RequestBody PostComment postComment, @PathVariable("IdPost") Long IdPost, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.addComment_to_Post(postComment,IdPost,u.getId());
	}
	
	@PostMapping("/add-Like-post/{IdPost}/{IdUser}")
	@ResponseBody
	public PostLike addLike_to_Post(@RequestBody PostLike postLike, @PathVariable("IdPost") Long IdPost, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.addLike_to_Post(postLike,IdPost,u.getId());
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
	public CommentLike addLike_to_Comment(@RequestBody CommentLike commentLike, @PathVariable("IdComment") Long IdComment, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.addLike_to_Comment(commentLike,IdComment,u.getId());
	}
	
	
	@PutMapping("/Update-Post/{IdPost}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> Update_Post(@RequestBody Post post, @PathVariable("IdPost") Long IdPost, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.Update_post(post,IdPost,u.getId());
	}
	
	@PutMapping("/Update-Adversting/{IdPost}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> Update_Adversting(@RequestBody Advertising a, @PathVariable("IdPost") Long IdPost) {
		return forumService.Update_Adversting(a,IdPost);
	}
	
	
	@PutMapping("/Update-Comment/{IdPostCom}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> Update_Comment(@RequestBody PostComment postComment, @PathVariable("IdPostCom") Long IdPostCom, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.Update_Comment(postComment,IdPostCom,u.getId());
	}
	
	@PutMapping("/Update-Likes-Dislikes/{IdLike}")
	@ResponseBody
	public ResponseEntity<?> Swap_like_dislike( @PathVariable("IdLike") Long IdLike) {
		return forumService.Swap_like_dislike(IdLike);
	}
	
	 
	
	@GetMapping("/Get-all-Post")
	public List<Post> Get_all_post(){
		return forumService.Get_all_post();
	}
	
	@GetMapping("/Get-Posts-By-user/{IdUser}")
	public Set<Post> Get_post_by_User( @PathVariable("IdUser") Long IdUser){
		return forumService.Get_post_by_User(IdUser);
	}
	
	@DeleteMapping("/Delete-Like/{IdLike}")
	public ResponseEntity<?> Delete_Like( @PathVariable("IdLike") Long IdLike, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.Delete_Like(IdLike,u.getId());
	}
	
	
	@GetMapping("/Get-post-Likes/{IdPost}")
	public Set<PostLike> Get_post_Likes( @PathVariable("IdPost") Long IdPost){
		return forumService.Get_post_Likes(IdPost);
	}
	

	@GetMapping("/Get-post-DisLikes/{IdPost}")
	public Set<PostLike> Get_post_DisLikes( @PathVariable("IdPost") Long IdPost){
		return forumService.Get_post_DisLikes(IdPost);
	}
	
	
	@DeleteMapping("/Delete-Post/{IdPost}")
	public ResponseEntity<?> Delete_Post( @PathVariable("IdPost") Long IdPost, @ApiIgnore @AuthenticationPrincipal UserPrincipal u) {
		return forumService.Delete_post(IdPost,u.getId());
	}
	
	@DeleteMapping("/Delete-Adversting/{IdAdv}")
	public ResponseEntity<?> Delete_Adversting( @PathVariable("IdAdv") Long IdPost) {
		return forumService.Delete_Adversting(IdPost);
	}
	
	@DeleteMapping("/Delete-PostComment/{IdPostCom}/{IdUser}")
	public ResponseEntity<?> Delete_PostCom( @PathVariable("IdPostCom") Long IdPostCom, @PathVariable("IdUser") Long IdUser) {
		return forumService.Delete_PostCom(IdPostCom,IdUser);
	}
	
	@DeleteMapping("/Delete-Post-Redandant")
	public void Delete_post_Redendant( ){
		 forumService.delete_sujet_sans_Int();
	}
	@GetMapping("/Get-best-podt-week")
	public Post Get_best_Post( ){
		return forumService.Get_best_Post();
	}
	
	@GetMapping("/Get-Post-Comments/{IdPost}")
	public  Set<PostComment> Get_post_Comm( @PathVariable("IdPost") Long IdPost ){
		return forumService.Get_post_Comm (IdPost);
	}
	
	@GetMapping("/Get-Comment-Comments/{idComment}")
	public  Set<PostComment> Get_comm_Comm( @PathVariable("idComment") Long idComment ){
		return forumService.Get_comm_Comm (idComment);
	}
	
	@PutMapping("/Give-post-etoile/{idPost}/{nb_etoile}")
	public  Post Give_Etoile_Post(@PathVariable("idPost") Long idPost, @PathVariable("nb_etoile") int nb_etoile ){
		return forumService.Give_Etoile_Post (idPost,nb_etoile);
	}
	
	@PutMapping("/Report-Post/{idPost}")
	public  Post Report_User(@PathVariable("idPost") Long idPost ){
		return forumService.Report_User (idPost);
	}
	
	@DeleteMapping("/Delete-reported-Post")
	public  void Delete_reported_post(){
		 forumService.delete_reported_post();
	}
	
	@GetMapping("/Get-More-Likers-User")
	public  Set<Object> Get_more_likers_user(){
		 return forumService.Get_more_likers_user();
	}
	
	@PostMapping("/add-categor-adv")
	public  ResponseEntity<?> addCategorAdv(@RequestBody CategoryAdve a ){
		 return forumService.addCategoryAdv(a);
	}
	
	
	@PutMapping("/test-Data")
	public  void aa(@ApiIgnore @AuthenticationPrincipal UserPrincipal u){
		forumService.DetctaDataLoad("sabri krima",u.getId());
	}
	
	
	@GetMapping("/Adversting-By-Loaddata")
	public  List<Advertising> adversting_bydata(@ApiIgnore @AuthenticationPrincipal UserPrincipal u){
		return forumService.getAdverByUserData(u.getId());
	}
	
	
}

