import { TestBed } from '@angular/core/testing';

import { ChaptercatalogService } from './chaptercatalog.service';

describe('ChaptercatalogService', () => {
  let service: ChaptercatalogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChaptercatalogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
