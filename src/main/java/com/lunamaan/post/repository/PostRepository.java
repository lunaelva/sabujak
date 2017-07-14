/**
 * 
 */
package com.lunamaan.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunamaan.post.entity.Post;

/**
 * @author maelva
 *
 */
public interface PostRepository extends JpaRepository<Post, Long>{
	

}
