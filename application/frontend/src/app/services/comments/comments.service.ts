import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {v4 as uuidv4} from 'uuid';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(
    private readonly httpClient: HttpClient
  ) {
  }

  public publishComment(command: PublishCommentCommand): Observable<PublishCommentResponse> {
    return this.httpClient
      .put<PublishCommentResponse>(
        `http://localhost:8080/api/comments/${uuidv4()}`,
        JSON.stringify({
          addToPostId: command.addToPostId,
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

export interface PublishCommentCommand {
  addToPostId: string;
  content: string;
}

export interface PublishCommentResponse {
  id: string;
  content: string;
}
