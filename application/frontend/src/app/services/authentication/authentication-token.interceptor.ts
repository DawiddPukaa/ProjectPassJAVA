import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CurrentUser, UserAuthenticationService} from './user-authentication.service';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationTokenInterceptor implements HttpInterceptor {

  constructor(private readonly userAuthenticationService: UserAuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser: CurrentUser = this.userAuthenticationService.currentUser;

    if (currentUser.isAuthenticated()) {
      return next.handle(req.clone({
        setHeaders: {
          Authorization: `token ${currentUser.getToken()}`
        }
      }));
    } else {
      return next.handle(req);
    }
  }
}
