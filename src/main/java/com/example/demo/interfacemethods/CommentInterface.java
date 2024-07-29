package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.Comments;

public interface CommentInterface {

	Comments saveComment (Comments comment);
	List<Comments> getCommentsByPostId(int postId);
}
