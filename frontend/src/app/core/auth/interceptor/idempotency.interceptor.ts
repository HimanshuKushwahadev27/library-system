import { HttpInterceptorFn } from '@angular/common/http';
import { randomUUID } from 'crypto';

export const idempotencyInterceptor: HttpInterceptorFn = (req, next) => {

  if (req.method === 'POST') {

    const cloned = req.clone({
      setHeaders: {
        'IDEMPOTENCY_HEADER': randomUUID()
      }
    });

    return next(cloned);
  }

  return next(req);
};