import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {
  AuthenticateWithCredentialsCommand,
  UserAuthenticationService
} from '../services/authentication/user-authentication.service';
import {Router} from '@angular/router';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-page-login',
  templateUrl: './page-login.component.html',
  styleUrls: ['./page-login.component.less']
})
export class PageLoginComponent implements OnInit {
  private readonly usernameField = new FormControl('', Validators.required);
  private readonly passwordField = new FormControl('', Validators.required);

  signinForm = new FormGroup({
    username: this.usernameField,
    password: this.passwordField,
  });

  signinInProgress = false;

  constructor(
    private readonly userAuthenticationService: UserAuthenticationService,
    private readonly router: Router
  ) {
  }

  ngOnInit(): void {
  }

  onSigninClicked(): void {
    if (!this.signinInProgress && this.signinForm.valid) {
      this.usernameField.disable();
      this.passwordField.disable();
      this.signinInProgress = true;

      this.userAuthenticationService
        .authenticateWithCredentials(new AuthenticateWithCredentialsCommand(
          this.signinForm.get('username').value,
          this.signinForm.get('password').value,
        ))
        .pipe(finalize(() => {
          this.usernameField.enable();
          this.passwordField.enable();
          this.signinInProgress = false;
        }))
        .subscribe(
          value => {
            if (value.authenticated) {
              this.router.navigate(['posts']);
            }
          },
          error => {
            console.log(error.message);
          }
        );
    }
  }

  onSignupClicked(): void {
    this.router.navigate(['signup']);
  }
}
