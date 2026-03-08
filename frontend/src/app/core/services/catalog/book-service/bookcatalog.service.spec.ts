import { TestBed } from '@angular/core/testing';

import { BookcatalogService } from './bookcatalog.service';

describe('BookcatalogService', () => {
  let service: BookcatalogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookcatalogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
