/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.jaas;

import com.openkm.core.AccessDeniedException;
import com.openkm.module.db.stuff.DbSessionManager;
import org.springframework.security.core.Authentication;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import java.security.Principal;

/**
 * @author pavila
 */
public class PrincipalUtils {
	/**
	 * Obtain current authenticated subject
	 */
	public static Subject getSubject() throws NamingException {
		InitialContext ctx = new InitialContext();
		return (Subject) ctx.lookup("java:comp/env/security/subject");
	}

	/**
	 * Obtain the logged user.
	 */
	public static Principal getUser() throws NamingException {
		return PrincipalUtils.getSubject().getPrincipals().stream().findFirst().get();
	}

	/**
	 * Get Authentication by token and also set it as current Authentication.
	 */
	public static Authentication getAuthenticationByToken(String token) throws AccessDeniedException {
		Authentication auth = DbSessionManager.getInstance().getAuthentication(token);

		if (auth != null) {
			return auth;
		} else {
			throw new AccessDeniedException("Invalid token: " + token);
		}
	}
}
