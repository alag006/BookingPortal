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

@Api(name = "addressendpoint", namespace = @ApiNamespace(ownerDomain = "techm.com", ownerName = "techm.com", packagePath = "bookingportal"))
public class AddressEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAddress")
	public CollectionResponse<Address> listAddress(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Address> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Address as Address");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Address>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Address obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Address> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAddress")
	public Address getAddress(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Address address = null;
		try {
			address = mgr.find(Address.class, id);
		} finally {
			mgr.close();
		}
		return address;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param address the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAddress")
	public Address insertAddress(Address address) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsAddress(address)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(address);
		} finally {
			mgr.close();
		}
		return address;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param address the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAddress")
	public Address updateAddress(Address address) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsAddress(address)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(address);
		} finally {
			mgr.close();
		}
		return address;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeAddress")
	public void removeAddress(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Address address = mgr.find(Address.class, id);
			mgr.remove(address);
		} finally {
			mgr.close();
		}
	}

	private boolean containsAddress(Address address) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Address item = mgr.find(Address.class, address.getAddressId());
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
