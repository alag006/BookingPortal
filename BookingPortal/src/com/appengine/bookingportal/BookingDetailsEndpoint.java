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

@Api(name = "bookingdetailsendpoint", namespace = @ApiNamespace(ownerDomain = "techm.com", ownerName = "techm.com", packagePath = "bookingportal"))
public class BookingDetailsEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listBookingDetails")
	public CollectionResponse<BookingDetails> listBookingDetails(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<BookingDetails> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from BookingDetails as BookingDetails");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<BookingDetails>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (BookingDetails obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<BookingDetails> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getBookingDetails")
	public BookingDetails getBookingDetails(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		BookingDetails bookingdetails = null;
		try {
			bookingdetails = mgr.find(BookingDetails.class, id);
		} finally {
			mgr.close();
		}
		return bookingdetails;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param bookingdetails the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertBookingDetails")
	public BookingDetails insertBookingDetails(BookingDetails bookingdetails) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsBookingDetails(bookingdetails)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(bookingdetails);
		} finally {
			mgr.close();
		}
		return bookingdetails;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param bookingdetails the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateBookingDetails")
	public BookingDetails updateBookingDetails(BookingDetails bookingdetails) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsBookingDetails(bookingdetails)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(bookingdetails);
		} finally {
			mgr.close();
		}
		return bookingdetails;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeBookingDetails")
	public void removeBookingDetails(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			BookingDetails bookingdetails = mgr.find(BookingDetails.class, id);
			mgr.remove(bookingdetails);
		} finally {
			mgr.close();
		}
	}

	private boolean containsBookingDetails(BookingDetails bookingdetails) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			BookingDetails item = mgr.find(BookingDetails.class,
					bookingdetails.getOrderId());
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
