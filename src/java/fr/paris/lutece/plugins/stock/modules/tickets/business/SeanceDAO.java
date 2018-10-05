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

import fr.paris.lutece.plugins.stock.business.attribute.offer.OfferAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.utils.AttributeDateUtils;
import fr.paris.lutece.plugins.stock.business.offer.Offer;
import fr.paris.lutece.plugins.stock.business.offer.OfferDAO;
import fr.paris.lutece.plugins.stock.business.offer.OfferFilter;
import fr.paris.lutece.plugins.stock.business.offer.OfferGenre;
import fr.paris.lutece.plugins.stock.business.offer.OfferGenre_;
import fr.paris.lutece.plugins.stock.business.offer.Offer_;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.product.Product_;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.plugins.stock.utils.jpa.StockJPAUtils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Specific DAO for Seance
 *
 * @author abataille
 */
public class SeanceDAO extends OfferDAO<Integer, Offer> implements ISeanceDAO
{
    /**
     * Build the criteria query used when offers are searched by filter
     * 
     * @param filter
     *            the filter
     * @param root
     *            the offer root
     * @param query
     *            the criteria query
     * @param builder
     *            the criteria builder
     */
    protected void buildCriteriaQuery( OfferFilter filter, Root<Offer> root, CriteriaQuery<Offer> query, CriteriaBuilder builder )
    {
        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        Join<Offer, Product> product = root.join( Offer_.product, JoinType.INNER );
        Join<Offer, OfferGenre> type = root.join( Offer_.type, JoinType.INNER );

        if ( StringUtils.isNotBlank( filter.getProductName( ) ) )
        {
            listPredicates.add( builder.like( product.get( Product_.name ), StockJPAUtils.buildCriteriaLikeString( filter.getProductName( ) ) ) );
        }

        if ( StringUtils.isNotBlank( filter.getName( ) ) )
        {
            listPredicates.add( builder.like( root.get( Offer_.name ), StockJPAUtils.buildCriteriaLikeString( filter.getName( ) ) ) );
        }

        if ( filter instanceof SeanceFilter )
        {
            SeanceFilter seanceFilter = (SeanceFilter) filter;

            // Date from (= date of seance <= date from)
            if ( seanceFilter.getDateBegin( ) != null )
            {
                Timestamp dateFrom = DateUtils.getDate( seanceFilter.getDateBegin( ), false );
                Join<Offer, OfferAttributeDate> join = root.join( Offer_.attributeDateList );
                listPredicates.add( AttributeDateUtils.greaterThanOrEqualTo( builder, join, SeanceDTO.ATTR_DATE, dateFrom ) );
            }

            // Date to (=date of seance >= date to)
            if ( seanceFilter.getDateEnd( ) != null )
            {
                Timestamp dateEnd = DateUtils.getDate( seanceFilter.getDateEnd( ), false );
                Join<Offer, OfferAttributeDate> join = root.join( Offer_.attributeDateList );
                listPredicates.add( AttributeDateUtils.lessThanOrEqualTo( builder, join, SeanceDTO.ATTR_DATE, dateEnd ) );
            }

            // Date the (=date seance == date the)
            if ( seanceFilter.getDateOr( ) != null )
            {
                Timestamp dateThe = DateUtils.getDate( seanceFilter.getDateOr( ), false );
                Join<Offer, OfferAttributeDate> join = root.join( Offer_.attributeDateList );
                listPredicates.add( AttributeDateUtils.equal( builder, join, SeanceDTO.ATTR_DATE, dateThe ) );
            }

            // Hour
            if ( seanceFilter.getHour( ) != null )
            {
                Date hour = DateUtils.getHourWithoutDate( seanceFilter.getHour( ) );
                Join<Offer, OfferAttributeDate> join = root.join( Offer_.attributeDateList );
                listPredicates.add( AttributeDateUtils.equal( builder, join, SeanceDTO.ATTR_HOUR, new Timestamp( hour.getTime( ) ) ) );
            }
        }

        if ( ( filter.getIdGenre( ) != null ) && ( filter.getIdGenre( ) > 0 ) )
        {
            listPredicates.add( builder.equal( type.get( OfferGenre_.id ), filter.getIdGenre( ) ) );
        }

        if ( ( filter.getProductId( ) != null ) && ( filter.getProductId( ) > 0 ) )
        {
            listPredicates.add( builder.equal( product.get( Product_.id ), filter.getProductId( ) ) );
        }

        if ( !listPredicates.isEmpty( ) )
        {
            // add existing predicates to Where clause
            query.where( listPredicates.toArray( new Predicate [ 0] ) );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.modules.tickets.business.ISeanceDAO#findAvailableSeanceByDate(java.sql.Timestamp)
     */

    /**
     * {@inheritDoc}
     */
    public List<Offer> findAvailableSeanceByDate( Integer offerId, Timestamp dateHour )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<Offer> cq = cb.createQuery( Offer.class );

        Root<Offer> root = cq.from( Offer.class );

        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        Join<Offer, Product> product = root.join( Offer_.product, JoinType.INNER );
        cq.distinct( true );

        // Date and hour criteria
        if ( dateHour != null )
        {
            Timestamp dateThe = DateUtils.getDate( dateHour, false );
            listPredicates.add( AttributeDateUtils.equal( cb, root.join( Offer_.attributeDateList ), SeanceDTO.ATTR_DATE, dateThe ) );

            Date hour = DateUtils.getHourWithoutDate( dateHour );
            listPredicates.add( AttributeDateUtils.equal( cb, root.join( Offer_.attributeDateList ), SeanceDTO.ATTR_HOUR, new Timestamp( hour.getTime( ) ) ) );
        }

        // Offer id
        if ( ( offerId != null ) && ( offerId > 0 ) )
        {
            listPredicates.add( cb.equal( product.get( Product_.id ), offerId ) );
        }

        // Status not defined
        listPredicates.add( cb.or( cb.isNull( root.get( Offer_.statut ) ), cb.equal( root.get( Offer_.statut ), "" ) ) );

        cq.where( listPredicates.toArray( new Predicate [ 0] ) );

        return em.createQuery( cq ).getResultList( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.modules.tickets.business.ISeanceDAO# findAvailableSeance(java.lang.Integer)
     */

    /**
     * {@inheritDoc}
     */
    public List<Offer> findAvailableSeance( Integer offerId )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<Offer> cq = cb.createQuery( Offer.class );

        Root<Offer> root = cq.from( Offer.class );

        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        Join<Offer, Product> product = root.join( Offer_.product, JoinType.INNER );

        // Offer id
        if ( ( offerId != null ) && ( offerId > 0 ) )
        {
            listPredicates.add( cb.equal( product.get( Product_.id ), offerId ) );
        }

        // Status not defined
        listPredicates.add( cb.or( cb.isNull( root.get( Offer_.statut ) ), cb.equal( root.get( Offer_.statut ), "" ) ) );

        cq.where( listPredicates.toArray( new Predicate [ 0] ) );

        return em.createQuery( cq ).getResultList( );
    }

    /**
     * Add the order by parameter to the query
     * 
     * @param filter
     *            the filter
     * @param root
     *            the offer root
     * @param query
     *            the criteria query
     * @param builder
     *            the criteria builder
     */
    protected void buildSortQuery( OfferFilter filter, Root<Offer> root, CriteriaQuery<Offer> query, CriteriaBuilder builder )
    {
        if ( ( filter.getOrders( ) != null ) && !filter.getOrders( ).isEmpty( ) )
        {
            List<Order> orderList = new ArrayList<Order>( );
            Join<Offer, Product> product = root.join( Offer_.product, JoinType.INNER );
            Join<Offer, OfferGenre> type = root.join( Offer_.type, JoinType.INNER );

            if ( filter.isOrderAsc( ) )
            {
                // get asc order
                for ( String order : filter.getOrders( ) )
                {
                    if ( order.equals( "product.name" ) )
                    {
                        orderList.add( builder.asc( product.get( "name" ) ) );
                    }
                    else
                        if ( order.equals( "typeName" ) )
                        {
                            orderList.add( builder.asc( type.get( "name" ) ) );
                        }
                        else
                            if ( order.equals( "date" ) )
                            {
                                Join<Offer, OfferAttributeDate> joinDate = root.join( Offer_.attributeDateList );
                                addRestriction( query, builder.equal( joinDate.get( "key" ), "date" ) );
                                orderList.add( builder.desc( joinDate.get( "value" ) ) );

                                Join<Offer, OfferAttributeDate> joinHour = root.join( Offer_.attributeDateList );
                                addRestriction( query, builder.equal( joinHour.get( "key" ), "hour" ) );
                                orderList.add( builder.asc( joinHour.get( "value" ) ) );
                            }
                            else
                            {
                                orderList.add( builder.asc( root.get( order ) ) );
                            }
                }
            }
            else
            {
                // get desc order
                for ( String order : filter.getOrders( ) )
                {
                    if ( order.equals( "product.name" ) )
                    {
                        orderList.add( builder.desc( product.get( "name" ) ) );
                    }
                    else
                        if ( order.equals( "typeName" ) )
                        {
                            orderList.add( builder.desc( type.get( "name" ) ) );
                        }
                        else
                            if ( order.equals( "date" ) )
                            {
                                Join<Offer, OfferAttributeDate> joinDate = root.join( Offer_.attributeDateList );
                                addRestriction( query, builder.equal( joinDate.get( "key" ), "date" ) );
                                orderList.add( builder.asc( joinDate.get( "value" ) ) );

                                Join<Offer, OfferAttributeDate> joinHour = root.join( Offer_.attributeDateList );
                                addRestriction( query, builder.equal( joinHour.get( "key" ), "hour" ) );
                                orderList.add( builder.desc( joinHour.get( "value" ) ) );
                            }
                            else
                            {
                                orderList.add( builder.desc( root.get( order ) ) );
                            }
                }
            }

            query.orderBy( orderList );
        }
    }
}
