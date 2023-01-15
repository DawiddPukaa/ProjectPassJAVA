import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {UserAuthenticationService} from './user-authentication.service';

@Injectable({
  providedIn: 'root'
})
export class EnsureAuthenticatedGuard implements CanActivate {
  constructor(
    private readonly userAuthenticationService: UserAuthenticationService,
    private readonly router: Router
  ) {
  }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.userAuthenticationService.currentUser.isAuthenticated()) {
      return true;
    } else {
      this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
    }
  }
}
