import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {PostsService} from '../services/posts/posts.service';
import {finalize} from 'rxjs/operators';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.less']
})
export class AddPostComponent implements OnInit {
  private subjectInput = new FormControl('', [Validators.required, Validators.minLength(1), Validators.maxLength(30)]);
  private contentInput = new FormControl('', [Validators.required, Validators.minLength(1), Validators.maxLength(5000)]);

  addPostForm = new FormGroup({
    subject: this.subjectInput,
    content: this.contentInput,
  });

  publishingInProgress = false;

  constructor(
    private readonly postsService: PostsService,
    private readonly router: Router
  ) {
  }

  ngOnInit(): void {
  }

  onPublishClicked(): void {
    if (!this.publishingInProgress && this.addPostForm.valid) {
      this.subjectInput.disable();
      this.contentInput.disable();
      this.publishingInProgress = true;

      this.postsService
        .publishPost({
          subject: this.subjectInput.value,
          content: this.contentInput.value,
        })
        .pipe(finalize(() => {
          this.subjectInput.enable();
          this.contentInput.enable();
          this.publishingInProgress = false;
        }))
        .subscribe(value => this.router.navigate(['posts']));
    }
  }
}
