package com.rasanusantara.ourrecipe.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rasanusantara.ourrecipe.dto.CommentRequest;
import com.rasanusantara.ourrecipe.dto.CommentResponse;
import com.rasanusantara.ourrecipe.model.Account;
import com.rasanusantara.ourrecipe.model.Admin;
import com.rasanusantara.ourrecipe.model.Comment;
import com.rasanusantara.ourrecipe.model.Recipe;
import com.rasanusantara.ourrecipe.repository.AccountRepository;
import com.rasanusantara.ourrecipe.repository.CommentRepository;
import com.rasanusantara.ourrecipe.repository.RecipeRepository;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // Endpoint: GET /api/comments?recipeId=1
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam(required = false) Long recipeId) {
        List<Comment> comments;
        if (recipeId != null) {
            comments = commentRepository.findByRecipeId(recipeId);
        } else {
            comments = commentRepository.findAll();
        }
        List<CommentResponse> response = comments.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Endpoint: POST /api/comments
    @PostMapping
    public ResponseEntity<CommentResponse> createOrUpdateComment(@RequestBody CommentRequest commentRequest) {
        if (commentRequest.getRecipeId() == null || commentRequest.getAccountId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RecipeId dan AccountId wajib diisi");
        }
        if (commentRequest.getRating() < 1 || commentRequest.getRating() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating harus antara 1 sampai 5");
        }

        Account account = accountRepository.findById(commentRequest.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));
        Recipe recipe = recipeRepository.findById(commentRequest.getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resep tidak ditemukan"));

        Comment comment = commentRepository.findByRecipeIdAndAccountId(recipe.getId(), account.getId())
                .orElseGet(() -> {
                    Comment newComment = new Comment();
                    newComment.setAccount(account);
                    newComment.setRecipe(recipe);
                    newComment.setCreatedAt(LocalDateTime.now());
                    return newComment;
                });

        comment.setText(commentRequest.getText());
        comment.setRating(commentRequest.getRating());
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now());
        }
        Comment saved = commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    // Endpoint: DELETE /api/comments/{id}?requesterId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @RequestParam Long requesterId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Komentar tidak ditemukan"));

        Account requester = accountRepository.findById(requesterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (requester instanceof Admin) {
            commentRepository.delete(comment);
            return ResponseEntity.ok("Komentar berhasil dihapus oleh admin");
        }

        if (comment.getAccount() != null && comment.getAccount().getId().equals(requester.getId())) {
            commentRepository.delete(comment);
            return ResponseEntity.ok("Komentar berhasil dihapus");
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Anda tidak memiliki izin menghapus komentar ini");
    }

    private CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getRecipe().getId(),
                comment.getAccount().getEmail(),
                comment.getAccount().getUsername(),
                comment.getText(),
                comment.getRating(),
                comment.getCreatedAt().toString()
        );
    }
}
