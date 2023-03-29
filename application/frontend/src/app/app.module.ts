import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSliderModule} from '@angular/material/slider';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {AddPostComponent} from './add-post/add-post.component';
import {MatMenuModule} from '@angular/material/menu';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatRadioModule} from '@angular/material/radio';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {ListPostsComponent} from './list-posts/list-posts.component';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {PageLoginComponent} from './page-login/page-login.component';
import {MainComponent} from './main/main.component';
import {PageSignupComponent} from './page-signup/page-signup.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {AuthenticationRelatedErrorHandlingInterceptor} from './services/authentication/authentication-related-error-handling.interceptor';
import {AuthenticationTokenInterceptor} from './services/authentication/authentication-token.interceptor';
import { PagePostDetailsComponent } from './page-post-details/page-post-details.component';
import {UploadFilesComponent} from './upload-files/upload-files.component';

@NgModule({
  declarations: [
    AppComponent,
    AddPostComponent,
    ListPostsComponent,
    PageLoginComponent,
    MainComponent,
    PageSignupComponent,
    PagePostDetailsComponent,
    UploadFilesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    FlexLayoutModule,
    MatSidenavModule,
    MatDividerModule,
    MatListModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    HttpClientModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    {provide: HTTP_INTERCEPTORS, useClass: AuthenticationRelatedErrorHandlingInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: AuthenticationTokenInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
