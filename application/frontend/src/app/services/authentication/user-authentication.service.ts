import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Token} from "@angular/compiler";
import {TokenstoreService} from "../tokenstore/tokenstore.service";

@Injectable({
  providedIn: 'root'
})
export class UserAuthenticationService {
  readonly currentUser: CurrentUser
//przenieść to do ciała konstrutora
  constructor(
    private readonly httpClient: HttpClient,
    private readonly tokenstoreService: TokenstoreService
  ) {
    this.currentUser = new CurrentUser(TokenstoreService);
  }

  public signupUser(command: SignupUserCommand): Observable<SignupUserResult> {
    return this.httpClient
      .post<SignupUserResponse>(
        'http://localhost:8080/authentication/signup',
        JSON.stringify({
          name: command.name,
          secondName: command.secondName,
          username: command.username,
          password: command.password
        }),
        {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          },
          responseType: 'json'
        }
      )
      .pipe(
        map((data: SignupUserResponse) => {
          this.currentUser.authenticateWith(data);

          return SignupUserResult.success();
        })
      );
  }


  public authenticateWithCredentials(command: AuthenticateWithCredentialsCommand): Observable<AuthenticationResult> {
    return this.httpClient
      .post<AuthenticateWithCredentialsResponse>(
        'http://localhost:8080/authentication/signin',
        new HttpParams()
          .set('username', command.username)
          .set('password', command.password),
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            Accept: 'application/json',
          },
          responseType: 'json'
        }
      )
      .pipe(
        map((data) => {
          this.currentUser.authenticateWith(data);

          return AuthenticationResult.authenticated();
        }),
        catchError((err: HttpErrorResponse) => {
          const response: Observable<AuthenticationResult> = Observable.create(AuthenticationResult.failed(err.message));
          return response;
        })
      );
  }

  public logout(): void {
    this.currentUser.clearAuthentication();
  }
}

export class CurrentUser {
  private authenticated: boolean;
  private token: string | undefined;

  constructor(private readonly TokenstoreService: TokenstoreService) {
    this.authenticated = false;
  }


  authenticateWith(data: AuthenticateWithCredentialsResponse): void {
    this.authenticated = true;
    this.token = data.token;
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  getToken(): string | undefined {
    return this.token;
  }

  clearAuthentication(): void {
    this.authenticated = false;
    this.token = undefined;
  }
}

interface AuthenticateWithCredentialsResponse {
  token: string;
}

export class AuthenticationResult {

  constructor(
    public readonly authenticated: boolean,
    public readonly cause: string | undefined
  ) {
  }

  public static authenticated(): AuthenticationResult {
    return new AuthenticationResult(true, undefined);
  }

  public static failed(cause: string): AuthenticationResult {
    return new AuthenticationResult(false, cause);
  }
}

export class AuthenticateWithCredentialsCommand {
  public readonly username: string;
  public readonly password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}

interface SignupUserResponse {
  token: string;
}

export class SignupUserResult {
  constructor(
    public readonly successfull: boolean,
    public readonly cause: string | undefined
  ) {
  }

  public static success(): SignupUserResult {
    return new SignupUserResult(true, undefined);
  }

  public static failed(cause: string): SignupUserResult {
    return new SignupUserResult(false, cause);
  }
}

export class SignupUserCommand {
  public readonly name: string;
  public readonly secondName: string;
  public readonly username: string;
  public readonly password: string;

  constructor(name: string, secondName: string, username: string, password: string) {
  this.name = name;
  this.secondName = secondName;
  this.username = username;
  this.password = password;
  }
}
