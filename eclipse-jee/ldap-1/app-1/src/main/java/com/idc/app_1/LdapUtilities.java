package com.idc.app_1;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.AddRequest;
import org.apache.directory.api.ldap.model.message.AddRequestImpl;
import org.apache.directory.api.ldap.model.message.AddResponse;
import org.apache.directory.api.ldap.model.message.DeleteRequest;
import org.apache.directory.api.ldap.model.message.DeleteRequestImpl;
import org.apache.directory.api.ldap.model.message.DeleteResponse;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.message.controls.ManageDsaITImpl;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// https://directory.apache.org/api/
// https://directory.apache.org/api/user-guide.html
// https://directory.apache.org/apacheds/advanced-user-guide.html
// http://joacim.breiler.com/apacheds/book.html

public class LdapUtilities {
	private static final Logger log = LoggerFactory.getLogger(LdapUtilities.class);
	private static final String HOST = "localhost";
	private static final int PORT = 10389;
	private static final String USER = "uid=admin,ou=system";
	private static final String PWD = "secret";

	public static LdapConnection connect() throws LdapException, IOException {
		log.debug(">>> connect");
		LdapConnection connection = new LdapNetworkConnection (HOST, PORT);
		log.debug("survived connection");
		connection.bind (USER, PWD);
		log.debug("<<< connect");
		return connection;
	}

	public static LdapConnection disconnect (LdapConnection connection) throws LdapException, IOException {
		log.debug(">>> disconnect");
		connection.unBind();
		connection.close();
		log.debug("<<< disconnect");
		return connection;
	}

	// https://directory.apache.org/api/user-guide/2.3-searching.html
	public static void getUser (String message, LdapConnection connection, String namedObject) throws LdapException, Exception {
		log.debug(">>> getUser; " + message);
		EntryCursor cursor = connection.search(namedObject, "(objectclass=*)", SearchScope.SUBTREE);
		while (cursor.next()) {
			Entry entry = cursor.get();
			Dn dn = entry.getDn();
			log.debug("DN "+dn.getName());
			Collection<Attribute> collection = entry.getAttributes();
			Iterator<Attribute> iter = collection.iterator();
			while (iter.hasNext()) {
				Attribute attr = iter.next();
				log.debug("key "+attr.getId()+" value "+attr.getString());
			}
		}
		log.debug("<<< getUser; " + message);
	}

	// https://directory.apache.org/api/user-guide/2.4-adding.html
	public static void addUser (String message, LdapConnection connection, String dn) throws LdapException, Exception {
		Entry entry = new DefaultEntry();
		entry.setDn(dn);
		entry.add ("ObjectClass", "inetOrgPerson");		// must be in this order
		entry.add ("ObjectClass", "organizationalPerson"); // must be ObjectClass: with no spaces.
		entry.add ("ObjectClass", "top");
		entry.add ("ObjectClass", "person");
		entry.add ("cn", "member full name");
		entry.add ("sn", "member last");
		entry.add ("givenName", "member given name");
		entry.add ("mail", "member99@mail.com");
		entry.add ("uid", "member99");
		entry.add ("userPassword", "password");

		Dn mydn = entry.getDn();
		log.debug("dn :"+mydn.getName());
		AddRequest addRequest = new AddRequestImpl();
		addRequest.setEntry(entry);
		addRequest.addControl(new ManageDsaITImpl());

		AddResponse response = connection.add(addRequest);
		log.debug("response code "+response.getLdapResult().getResultCode());
	}

	public static void deleteUser (String message, LdapConnection connection, String dn) throws LdapException, Exception {
		DeleteRequest deleteRequest = new DeleteRequestImpl();
		deleteRequest.setName(new Dn(dn));
		DeleteResponse response = connection.delete(deleteRequest);
		log.debug("response code "+response.getLdapResult().getResultCode());
	}

// https://directory.apache.org/api/user-guide/2.6-modifying.html
	public static void updateUser (String message, LdapConnection connection, String dn) throws LdapException, Exception {
		Modification replaceGn = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "givenName", "Jack");
		connection.modify(dn, replaceGn);
	}
}

/*
this works fine...

	public static void addUser (String message, LdapConnection connection) throws LdapException, Exception {
		Entry entry = new DefaultEntry(
				"uid=member996,ou=users,ou=system",
				"ObjectClass: inetOrgPerson",		// must be in this order
				"ObjectClass: organizationalPerson", // must be ObjectClass: with no spaces.
				"ObjectClass: top",
				"ObjectClass: person",
				"cn: common",
				"sn: sn",
				"mail: mail@mail.com"
				);
		Dn dn = entry.getDn();
		log.debug("dn :"+dn.getName());
		AddRequest addRequest = new AddRequestImpl();
		addRequest.setEntry(entry);
		addRequest.addControl(new ManageDsaITImpl());

		AddResponse response = connection.add(addRequest);
		log.debug("response code "+response.getLdapResult().getResultCode());
	}

*/
