import {Component, OnInit} from '@angular/core';
import {GetBlogPostResponse, PostsService} from '../services/posts/posts.service';
import {ActivatedRoute} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CommentsService} from '../services/comments/comments.service';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-page-post-details',
  templateUrl: './page-post-details.component.html',
  styleUrls: ['./page-post-details.component.less']
})
export class PagePostDetailsComponent implements OnInit {
  private postId: string;

  loading = true;
  publishingCommentInProgress = false;
  post: GetBlogPostResponse;

  private commentContentInput = new FormControl('', Validators.required);

  addCommentForm = new FormGroup({
    commentContent: this.commentContentInput
  });

  constructor(
    private readonly postsService: PostsService,
    private readonly commentsService: CommentsService,
    private readonly activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute
      .paramMap
      .subscribe(paramMap => {
        this.postId = paramMap.get('id');

        this.refreshPost();
      });
  }

  onPublishCommentClicked(): void {
    if (!this.publishingCommentInProgress && this.addCommentForm.valid) {
      this.commentContentInput.disable();

      this.commentsService
        .publishComment({
          addToPostId: this.postId,
          content: this.commentContentInput.value,
        })
        .pipe(finalize(() => this.commentContentInput.enable()))
        .subscribe(value => {
          this.commentContentInput.setValue('');
          this.refreshPost();
        });
    }
  }

  private refreshPost(): void {
    this.loading = true;
    this.postsService
      .getPost(this.postId)
      .subscribe(value => {
        this.post = value;
        this.loading = false;
      });
  }
}
