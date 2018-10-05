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

import fr.paris.lutece.plugins.stock.business.purchase.Purchase;
import fr.paris.lutece.plugins.stock.utils.jpa.StockJPAUtils;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * PurchaseStatistic
 *
 */
@Entity
@Table( name = "stock_ticket_purchase_statistic" )
public class PurchaseStatistic
{
    public static final String ATTRIBUTE_CHILDREN_LIST = "childrenList";
    public static final String ATTRIBUTE_PARENT = "parent";

    // /** Sequence name */
    private static final String JPA_SEQUENCE_NAME = "billeterie_purchase_statistic_sequence";

    /** Unique value */
    private static final String JPA_COLUMN_NAME = "billeterie_purchase_statistic_id";
    private Integer _id;
    private Integer _nDayOfYear;
    private Integer _nWeek;
    private Integer _nMonth;
    private Integer _nYear;
    private Timestamp _tDate;
    private Purchase _purchase;

    /**
     * Constructor
     */
    public PurchaseStatistic( )
    {
        super( );
    }

    /**
     * Build a new purchaseStatistic from a purchaseStatistic object
     * 
     * @param purchaseStatistic
     *            the purchaseStatistic
     */
    public PurchaseStatistic( PurchaseStatistic purchaseStatistic )
    {
        _id = purchaseStatistic.getId( );
        _nDayOfYear = purchaseStatistic.getDayOfYear( );
        _nWeek = purchaseStatistic.getWeek( );
        _nMonth = purchaseStatistic.getMonth( );
        _nYear = purchaseStatistic.getYear( );
        _purchase = purchaseStatistic.getPurchase( );
    }

    /**
     * Return purchase_statistic id
     * 
     * @return purchase_statistic id
     */
    @TableGenerator( table = StockJPAUtils.SEQUENCE_TABLE_NAME, name = JPA_SEQUENCE_NAME, pkColumnValue = JPA_COLUMN_NAME, allocationSize = 1 )
    @Id
    @GeneratedValue( strategy = GenerationType.TABLE, generator = JPA_SEQUENCE_NAME )
    @Column( name = "id_purchase_statistic" )
    public Integer getId( )
    {
        return _id;
    }

    /**
     * Set the purchase_statistic id.
     *
     * @param idPurchaseStatistic
     *            the new id
     */
    public void setId( Integer idPurchaseStatistic )
    {
        _id = idPurchaseStatistic;
    }

    /**
     * Return the purchase_statistic dayOfYear
     * 
     * @return the dayOfYear
     */
    @Column( name = "dayOfYear" )
    public Integer getDayOfYear( )
    {
        return _nDayOfYear;
    }

    /**
     * Set the purchase_statistic dayOfYear
     * 
     * @param dayOfYear
     *            the purchase_statistic dayOfYear
     */
    public void setDayOfYear( Integer dayOfYear )
    {
        _nDayOfYear = dayOfYear;
    }

    /**
     * Set the purchase_statistic week
     * 
     * @param week
     *            the purchase_statistic week
     */
    public void setWeek( Integer week )
    {
        _nWeek = week;
    }

    /**
     * Return the purchase_statistic week
     * 
     * @return the week
     */
    @Column( name = "week" )
    public Integer getWeek( )
    {
        return _nWeek;
    }

    /**
     * Set the purchase_statistic month
     * 
     * @param month
     *            the purchase_statistic month
     */
    public void setMonth( Integer month )
    {
        _nMonth = month;
    }

    /**
     * Return the purchase_statistic month
     * 
     * @return the month
     */
    @Column( name = "month" )
    public Integer getMonth( )
    {
        return _nMonth;
    }

    /**
     * Set the purchase_statistic year
     * 
     * @param year
     *            the purchase_statistic year
     */
    public void setYear( Integer year )
    {
        _nYear = year;
    }

    /**
     * Return the purchase_statistic year
     * 
     * @return the year
     */
    @Column( name = "year" )
    public Integer getYear( )
    {
        return _nYear;
    }

    /**
     * Set the purchase_statistic date
     * 
     * @param date
     *            the purchase_statistic date
     */
    public void setDate( Timestamp date )
    {
        _tDate = date;
    }

    /**
     * Return the purchase_statistic date
     * 
     * @return the date
     */
    @Column( name = "date" )
    public Timestamp getDate( )
    {
        return _tDate;
    }

    /**
     * Return the purchase
     * 
     * @return the purchase
     */
    @OneToOne( fetch = FetchType.LAZY )
    @OrderColumn
    public Purchase getPurchase( )
    {
        return _purchase;
    }

    /**
     * Set the purchase
     * 
     * @param purchase
     *            the purchase
     */
    public void setPurchase( Purchase purchase )
    {
        _purchase = purchase;
    }
}
