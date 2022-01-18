/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import java.util.Comparator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * This object store informations about a partner's contact
 */
public class Contact
{
    /**
     * comparator of contact, using ID parameter
     */
    public static final Comparator<Contact> COMPARATOR_USING_ID = new Comparator<Contact>( )
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare( Contact o1, Contact o2 )
        {
            int compare = 0;
            compare = ( o1.getId( ) < o2.getId( ) ) ? ( -1 ) : compare;
            compare = ( o1.getId( ) > o2.getId( ) ) ? 1 : compare;

            return compare;
        }
    };

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
     * Constructor of the contact
     * 
     * @param id
     *            the contact id
     * @param name
     *            the contact name
     * @param phoneNumber
     *            the contact phone number
     * @param mail
     *            the contact mail
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
     * 
     * @return the contact id
     */
    public int getId( )
    {
        return _id;
    }

    /**
     * Set the id
     * 
     * @param id
     *            The id
     */
    public void setId( int id )
    {
        this._id = id;
    }

    /**
     * get the name of the contact
     * 
     * @return the name
     */
    public String getName( )
    {
        return _name;
    }

    /**
     * Set the phone number
     * 
     * @param name
     *            the contact name
     */
    public void setName( String name )
    {
        this._name = name;
    }

    /**
     * get the adress mail of the contact
     * 
     * @return the adress mail
     */
    public String getMail( )
    {
        return _mail;
    }

    /**
     * Set the phone number
     * 
     * @param mail
     *            the contact adress mail
     */
    public void setMail( String mail )
    {
        this._mail = mail;
    }

    /**
     * get the phone number of the contact
     * 
     * @return the phone number
     */
    public String getPhoneNumber( )
    {
        return _phoneNumber;
    }

    /**
     * Set the phone number
     * 
     * @param phoneNumber
     *            the contact phone number
     */
    public void setPhoneNumber( String phoneNumber )
    {
        this._phoneNumber = phoneNumber;
    }

    /**
     * To know if two contact are the same
     * 
     * @param c
     *            the other contact
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals( Object c )
    {
        if ( c != null )
        {
            return ( (Contact) c ).getId( ) == this._id;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode( )
    {
        return getId( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString( )
    {
        return "Contact " + this._id + ":" + this._name + "," + this._mail + "," + this._phoneNumber + ";";
    }
}
