package fr.paris.lutece.plugins.stock.modules.tickets.business;

import java.util.Comparator;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * This object store informations about a partner's contact
 * @author jchaline
 * 
 */
public class Contact
{
    /**
     * Name of the contact, firstname and surname
     */
    private String _name;

    /**
     * adress mail of the contact
     */
    @Email
    @NotEmpty
    private String _mail;

    /**
     * phone number of the contact
     */
    private String _phoneNumber;

    /**
     * primary key for contact
     */
    private int _id;

    /**
     * comparator of contact, using ID parameter
     */
    public static final Comparator<Contact> COMPARATOR_USING_ID = new Comparator<Contact>( )
    {
        public int compare( Contact o1, Contact o2 )
        {
            int compare = 0;
            compare = o1.getId( ) < o2.getId( ) ? -1 : compare;
            compare = o1.getId( ) > o2.getId( ) ? 1 : compare;
            return compare;
        }
    };

    /**
     * Constructor of the contact
     * @param id the contact id
     * @param name the contact name
     * @param phoneNumber the contact phone number
     * @param mail the contact mail
     */
    public Contact( int id, String name, String phoneNumber, String mail )
    {
        this._id = id;
        this._name = name;
        this._mail = mail;
        this._phoneNumber = phoneNumber;
    }

    /**
     * default constructor
     */
    public Contact( )
    {

    }

    /**
     * get the id of the contact
     * @return the contact id
     */
    public int getId( )
    {
        return _id;
    }

    /**
     * Set the phone number
     * @param id
     */
    public void setId( int id )
    {
        this._id = id;
    }

    /**
     * get the name of the contact
     * @return the name
     */
    public String getName( )
    {
        return _name;
    }

    /**
     * Set the phone number
     * @param name the contact name
     */
    public void setName( String name )
    {
        this._name = name;
    }

    /**
     * get the adress mail of the contact
     * @return the adress mail
     */
    public String getMail( )
    {
        return _mail;
    }

    /**
     * Set the phone number
     * @param mail the contact adress mail
     */
    public void setMail( String mail )
    {
        this._mail = mail;
    }

    /**
     * get the phone number of the contact
     * @return the phone number
     */
    public String getPhoneNumber( )
    {
        return _phoneNumber;
    }

    /**
     * Set the phone number
     * @param phoneNumber the contact phone number
     */
    public void setPhoneNumber( String phoneNumber )
    {
        this._phoneNumber = phoneNumber;
    }

    /**
     * To know if two contact are the same
     * @param c the other contact
     * @return true if they are the same, false otherwise
     */
    public boolean equals( Object c )
    {
        return ( (Contact) c ).getId( ) == this._id;
    }

    public String toString( )
    {
        return "Contact " + this._id + ":" + this._name + "," + this._mail + "," + this._phoneNumber + ";";
    }

}
