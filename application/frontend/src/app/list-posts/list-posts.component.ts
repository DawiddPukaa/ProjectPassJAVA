import {Component, OnInit} from '@angular/core';
import {PostResponse, PostsService} from '../services/posts/posts.service';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-list-posts',
  templateUrl: './list-posts.component.html',
  styleUrls: ['./list-posts.component.less']
})
export class ListPostsComponent implements OnInit {
  posts: PostResponse[];
  loading = true;

  constructor(
    private readonly postsService: PostsService
  ) {
  }

  ngOnInit(): void {
    this.postsService
      .getAllPosts()
      .pipe(finalize(() => this.loading = false))
      .subscribe(
        value => this.posts = value.posts
      );
  }
}
