import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddPostComponent} from './add-post/add-post.component';
import {ListPostsComponent} from './list-posts/list-posts.component';
import {PageLoginComponent} from './page-login/page-login.component';
import {MainComponent} from './main/main.component';
import {PageSignupComponent} from './page-signup/page-signup.component';
import {EnsureAuthenticatedGuard} from './services/authentication/ensure-authenticated.guard';
import {PagePostDetailsComponent} from './page-post-details/page-post-details.component';
import {UploadFilesComponent} from "./upload-files/upload-files.component";

const routes: Routes = [
  {path: 'login', component: PageLoginComponent},
  {path: 'signup', component: PageSignupComponent},
  {path: 'upload', component: UploadFilesComponent},
  {path: '', component: MainComponent, canActivate: [EnsureAuthenticatedGuard],
    children: [
      {path: 'posts/add', component: AddPostComponent},
      {path: 'posts', component: ListPostsComponent},
      {path: 'posts/:id', component: PagePostDetailsComponent},
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
