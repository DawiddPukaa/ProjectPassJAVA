import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {SignupUserCommand, UserAuthenticationService} from '../services/authentication/user-authentication.service';
import {Router} from '@angular/router';
import {finalize} from 'rxjs/operators';
import {Z} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-page-signup',
  templateUrl: './page-signup.component.html',
  styleUrls: ['./page-signup.component.less']
})
export class PageSignupComponent implements OnInit {
  private nameField = new FormControl('',[Validators.required, Validators.pattern('[A-Za-z]{0,17}')]);
  private secondnameField = new FormControl('',[Validators.required, Validators.pattern('[A-Za-z]{0,27}')]);
  private usernameField = new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]);
  private passwordField = new FormControl('', Validators.required);
  private confirmPasswordField = new FormControl('', [Validators.required, this.fieldValueEqualityValidator('password')]);

  signupForm = new FormGroup({
    name: this.nameField,
    secondName:this.secondnameField,
    username: this.usernameField,
    password: this.passwordField,
    confirmPassword: this.confirmPasswordField,
  });

  signupInProgress = false;

  constructor(
    private readonly userAuthenticationService: UserAuthenticationService,
    private readonly router: Router
  ) {

  }

  ngOnInit(): void {
  }

  public onSignupSubmit(): void {
    if (this.signupForm.valid) {
      this.signupInProgress = true;
      this.nameField.disable();
      this.secondnameField.disable();
      this.usernameField.disable();
      this.passwordField.disable();
      this.confirmPasswordField.disable();

      this.userAuthenticationService
        .signupUser(new SignupUserCommand(
          this.signupForm.get('name').value,
          this.signupForm.get('secondName').value,
          this.signupForm.get('username').value,
          this.signupForm.get('password').value,
        ))
        .pipe(finalize(() => {
          this.usernameField.enable();
          this.nameField.enable();
          this.secondnameField.enable();
          this.passwordField.enable();
          this.confirmPasswordField.enable();
        }))
        .subscribe(
          (result) => {
            if (result.successfull) {
              this.router.navigate(['posts']);
            }
          },
          error => console.log(error)
        );
    }
  }

  private fieldValueEqualityValidator(otherFieldName: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const otherFieldValue = control.parent?.get(otherFieldName)?.value;
      const result = otherFieldValue && otherFieldValue === control.value;

      return result ? null : {fieldValueNotTheSame: {value: control.value}};
    };
  }
}
