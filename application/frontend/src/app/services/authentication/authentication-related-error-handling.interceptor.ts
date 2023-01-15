import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {UserAuthenticationService} from './user-authentication.service';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationRelatedErrorHandlingInterceptor implements HttpInterceptor {

  constructor(private readonly userAuthenticationService: UserAuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(catchError(err => {
        if ((req.url.endsWith('/authentcation/signup') || req.url.endsWith('/authentcation/signin')) && err.status === 401) {
          this.userAuthenticationService.logout();

          location.reload(true);
        }

        return throwError(err.error.message || err.statusText);
      }));
  }
}
