import { TestBed } from '@angular/core/testing';

import { TokenstoreService } from './tokenstore.service';

describe('CommentsService', () => {
  let service: TokenstoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TokenstoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
