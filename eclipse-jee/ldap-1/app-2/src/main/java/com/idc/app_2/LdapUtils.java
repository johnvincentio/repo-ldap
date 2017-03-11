package com.idc.app_2;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/*
 * ref:
 * http://www.coderpanda.com/tag/apache-ds-tutorial/
 * 
 * Using Demo data
 */
public class LdapUtils {

	public static DirContext connect() {
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		properties.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		properties.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext context = null;
		try {
			context = new InitialDirContext(properties);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}

	public static void doLookup(String message, DirContext context, String namedObject, String[] requiredAttributes) {
		System.out.println(">>> doLookup; " + message);
		try {
			Attributes attrs = context.getAttributes(namedObject);
			displayAttributes (message, attrs);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< doLookup; " + message);
	}

	public static Attributes getUser (String message, DirContext context, String namedObject) {
		System.out.println(">>> getUser; " + message);
		Attributes attrs = null;
		try {
			attrs = context.getAttributes(namedObject);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< getUser; " + message);
		return attrs;
	}

	public static void doFullLookup(String message, DirContext context, String namedObject) {
		System.out.println(">>> doFullLookup; " + message);
		try {
			Attributes attrs = context.getAttributes(namedObject);
			displayAttributes(message, attrs);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< doFullLookup; " + message);
	}

	public static void doList(String message, DirContext context, String namedObject, String[] requiredAttributes) {
		System.out.println(">>> List; " + message);
		String searchFilter = "(objectClass=inetOrgPerson)";

		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(requiredAttributes);

		NamingEnumeration<SearchResult> users;
		try {
			System.out.println("Searching...");
			users = context.search(namedObject, searchFilter, controls);
			SearchResult searchResult = null;
			while (users.hasMore()) {
				System.out.println("Found next user...");
				searchResult = (SearchResult) users.next();
				Attributes attr = searchResult.getAttributes();
				displayAttributes (message, attr);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< List; " + message);
	}

	public static void addUser(DirContext context) {
		System.out.println(">>> addUser");
		Attributes attributes = new BasicAttributes();
		Attribute attribute = new BasicAttribute("objectClass");
		attribute.add("inetOrgPerson");
		attributes.put(attribute);

		Attribute sn = new BasicAttribute("sn");
		sn.add("member last");
		attributes.put(sn);

		Attribute cn = new BasicAttribute("cn");
		cn.add("member full name");
		attributes.put(cn);

		attributes.put("givenName", "member given name");
		attributes.put("mail", "member99@mail.com");

		Attribute pn = new BasicAttribute("userPassword", "password");
		attributes.put(pn);

		try {
			context.createSubcontext("uid=member99,ou=users,ou=system", attributes);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< addUser");
	}

	public static void deleteUser(DirContext context) {
		System.out.println(">>> deleteUser");
		try {
			context.destroySubcontext("uid=member99,ou=users,ou=system");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< deleteUser");
	}

	public static void updateUser (DirContext context, Attributes attrs, String namedObject) {
		System.out.println(">>> updateUser");
		try {
			Attribute mod = new BasicAttribute("mail", "new_mail@mail.com");
			ModificationItem[] item = new ModificationItem[1];
			item[0] = new ModificationItem (DirContext.REPLACE_ATTRIBUTE, mod);
			context.modifyAttributes (namedObject, item);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< updateUser");
		
	}

	public static void displayAttributes(String message, Attributes attributes) {
//		System.out.println(">>> displayAttributes; " + message);
		if (attributes != null) {
			try {
				NamingEnumeration<? extends Attribute> e = attributes.getAll();
				while (e.hasMore()) {
					Attribute attr = (Attribute) e.next();
					displayAttribute (message, attr);
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("<<< displayAttributes; " + message);
	}
	public static void displayAttribute (String message, Attribute attr) {
//		System.out.println(">>> displayAttribute; " + message);
		try {
			String id = attr.getID();
			if ("userPassword".equals(id)) {
				for (NamingEnumeration<?> n = attr.getAll(); n.hasMore();)
					System.out.println("Attribute name :" + attr.getID() + ": hash password "+new String ((byte[]) n.next()));
			}
			else {
				for (NamingEnumeration<?> n = attr.getAll(); n.hasMore();) 
					System.out.println("Attribute name :" + attr.getID() + ": value : " + n.next() + ":");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
//		System.out.println("<<< displayAttribute; " + message);
	}
	
	public static void doPasswordTest (String message, DirContext context, String namedObject) {
		System.out.println(">>> doPasswordTest; " + message);
		try {
			Attributes attrs = context.getAttributes(namedObject);
			Attribute pwd = attrs.get("userPassword");
			System.out.println("=> userPassword : " + new String((byte[])pwd.get()));
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("<<< doPasswordTest; " + message);
	}
}
