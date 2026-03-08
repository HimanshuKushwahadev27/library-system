import { TestBed } from '@angular/core/testing';

import { GenrecatalogService } from './genrecatalog.service';

describe('GenrecatalogService', () => {
  let service: GenrecatalogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenrecatalogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
