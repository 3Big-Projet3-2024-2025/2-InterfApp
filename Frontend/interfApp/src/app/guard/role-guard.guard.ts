import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../service/user.service';

export const roleGuardGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const router = inject(Router);

  const expectedRole = route.data?.['role'];

  if (userService.hasRole('ADMIN') || userService.hasRole(expectedRole)) {
    return true;
  } else {
    console.warn('Access denied - User does not have required role');
    router.navigate(['/login']);
    return false;
  }
};