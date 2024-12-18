import { HttpEvent, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { HttpHandlerFn } from '@angular/common/http';

// Define the interceptor function as per Angular 18+ standards
export function authInterceptor(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
    // Inject CookieService using inject() since this is a function-based interceptor
    const cookieService = inject(CookieService);
    
    // Retrieve the JWT token from cookies
    const token = cookieService.get("jwt");
    
    // If the token exists, clone the request and add the Authorization header
    if (token) {
      const clonedRequest = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });

      // Proceed with the modified request
      return next(clonedRequest);
    }

    // If no token, proceed with the original request
    return next(req);
}
