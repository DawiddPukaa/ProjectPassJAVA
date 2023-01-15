import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {v4 as uuidv4} from 'uuid';

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(
    private readonly httpClient: HttpClient
  ) {
  }

  getAllPosts(): Observable<GetAllBlogPostsResponse> {
    return this.httpClient
      .get<GetAllBlogPostsResponse>(
        'http://localhost:8080/api/posts',
        {
          headers: {
            Accept: 'application/json'
          }
        }
      );
  }

  getPost(postId: string): Observable<GetBlogPostResponse> {
    return this.httpClient
      .get<GetBlogPostResponse>(
        `http://localhost:8080/api/posts/${postId}`,
        {
          headers: {
            Accept: 'application/json'
          }
        }
      );
  }

  publishPost(command: PublishPostCommand): Observable<PublishPostResponse> {
    return this.httpClient
      .put<PublishPostResponse>(
        `http://localhost:8080/api/posts/${uuidv4()}`,
        JSON.stringify({
          subject: command.subject,
          content: command.content,
        }),
        {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          }
        }
      );
  }
}

export interface PublishPostCommand {
  subject: string;
  content: string;
}

export interface PublishPostResponse {
  id: string;
}

export interface GetAllBlogPostsResponse {
  posts: PostResponse[];
}

export interface PostResponse {
  id: string;
  subject: string;
  content: string;
  numberOfComments: number;
  postedBy: string;
  postedOn: string;
}

export interface GetBlogPostResponse {
  subject: string;
  content: string;
  postedBy: string;
  postedOn: string;
  comments: Comment[];
}

export interface Comment {
  author: string;
  commentPostedTimeAgo: string;
  content: string;
}
