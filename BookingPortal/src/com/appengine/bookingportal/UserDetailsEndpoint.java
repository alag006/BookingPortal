package com.appengine.bookingportal;

import com.appengine.bookingportal.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "userdetailsendpoint", namespace = @ApiNamespace(ownerDomain = "techm.com", ownerName = "techm.com", packagePath = "bookingportal"))
public class UserDetailsEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUserDetails")
	public CollectionResponse<UserDetails> listUserDetails(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<UserDetails> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from UserDetails as UserDetails");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<UserDetails>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (UserDetails obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<UserDetails> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUserDetails")
	public UserDetails getUserDetails(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		UserDetails userdetails = null;
		try {
			userdetails = mgr.find(UserDetails.class, id);
		} finally {
			mgr.close();
		}
		return userdetails;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param userdetails the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUserDetails")
	public UserDetails insertUserDetails(UserDetails userdetails) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUserDetails(userdetails)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(userdetails);
		} finally {
			mgr.close();
		}
		return userdetails;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param userdetails the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUserDetails")
	public UserDetails updateUserDetails(UserDetails userdetails) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUserDetails(userdetails)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(userdetails);
		} finally {
			mgr.close();
		}
		return userdetails;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUserDetails")
	public void removeUserDetails(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			UserDetails userdetails = mgr.find(UserDetails.class, id);
			mgr.remove(userdetails);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUserDetails(UserDetails userdetails) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			UserDetails item = mgr.find(UserDetails.class,
					userdetails.getUserId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
