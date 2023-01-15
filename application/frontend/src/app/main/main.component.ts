import {Component, OnInit} from '@angular/core';
import {UserAuthenticationService} from '../services/authentication/user-authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.less']
})
export class MainComponent implements OnInit {

  constructor(
    private readonly userAuthenticationService: UserAuthenticationService,
    private readonly router: Router,
  ) {
  }

  ngOnInit(): void {
  }

  onLogoutClicked(): void {
    this.userAuthenticationService.logout();
    this.router.navigate(['login']);
  }
}
