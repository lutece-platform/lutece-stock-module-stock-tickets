/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.stock.business.attribute.product.ProductAttributeDate;
import fr.paris.lutece.plugins.stock.business.attribute.product.ProductAttributeNum;
import fr.paris.lutece.plugins.stock.business.attribute.utils.AttributeDateUtils;
import fr.paris.lutece.plugins.stock.business.attribute.utils.AttributeNumUtils;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.product.ProductDAO;
import fr.paris.lutece.plugins.stock.business.product.ProductFilter;
import fr.paris.lutece.plugins.stock.business.product.Product_;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.utils.DateUtils;
import fr.paris.lutece.plugins.stock.utils.jpa.StockJPAUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;


/**
 * Specific Data Access methods
 * 
 * @author abataille
 */
public class ShowDAO extends ProductDAO<Integer, Product> implements IShowDAO
{


    /**
     * Build the criteria query from the filter
     * @param filter the filter
     * @param root the category root
     * @param query the criteria query
     * @param builder the criteria builder
     */
    protected void buildCriteriaQuery( ProductFilter filter, Root<Product> root, CriteriaQuery<Product> query,
            CriteriaBuilder builder )
    {
        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        if ( StringUtils.isNotBlank( filter.getName( ) ) )
        {
            listPredicates.add( builder.like( root.get( Product_.name ),
                    StockJPAUtils.buildCriteriaLikeString( filter.getName( ) ) ) );
        }

        if ( filter.getIdCategory( ) != null && filter.getIdCategory( ) > 0 )
        {
            listPredicates.add( builder.equal( root.get( Product_.category ), filter.getIdCategory( ) ) );
        }

        if ( filter.getIdProvider( ) != null && filter.getIdProvider( ) > 0 )
        {
            listPredicates.add( builder.equal( root.get( Product_.provider ), filter.getIdProvider( ) ) );
        }

        if ( filter.getIdProduct( ) != null && filter.getIdProduct( ) > 0 )
        {
            listPredicates.add( builder.equal( root.get( Product_.id ), filter.getIdProduct( ) ) );
        }

        // Date from (= date end of show <= date from)
        if ( StringUtils.isNotEmpty( filter.getDateFrom( ) ) )
        {
            Timestamp dateFrom = DateUtils.getDate( filter.getDateFrom( ), false );
            Join<Product, ProductAttributeDate> join = root.join( Product_.attributeDateList );
            listPredicates.add( AttributeDateUtils
                    .greaterThanOrEqualTo( builder, join, ShowDTO.ATTR_DATE_END, dateFrom ) );
        }
        // Date to (=date start of show >= date to)
        if ( StringUtils.isNotEmpty( filter.getDateTo( ) ) )
        {
            Timestamp dateEnd = DateUtils.getDate( filter.getDateTo( ), false );
            Join<Product, ProductAttributeDate> join = root.join( Product_.attributeDateList );
            listPredicates
                    .add( AttributeDateUtils.lessThanOrEqualTo( builder, join, ShowDTO.ATTR_DATE_START, dateEnd ) );
        }

        // Date the (=date the between date start and date end)
        if ( StringUtils.isNotEmpty( filter.getDateThe( ) ) )
        {
            Timestamp dateThe = DateUtils.getDate( filter.getDateThe( ), false );
            Join<Product, ProductAttributeDate> join = root.join( Product_.attributeDateList );
            listPredicates
                    .add( AttributeDateUtils.lessThanOrEqualTo( builder, join, ShowDTO.ATTR_DATE_START, dateThe ) );
            listPredicates
                    .add( AttributeDateUtils.greaterThanOrEqualTo( builder, join, ShowDTO.ATTR_DATE_END, dateThe ) );
        }

        // En page d'accueil (à l'affiche)
        if ( filter.getAlaffiche( ) != null && filter.getAlaffiche( ) )
        {
            Join<Product, ProductAttributeNum> join = root.join( "attributeNumList" );
            // Join<Product, ProductAttributeNum> join = root.join(
            // Product_.attributeNumList );
            listPredicates
                    .add( AttributeNumUtils.equal( builder, join, ShowDTO.ATTR_A_LAFFICHE, new BigDecimal( 1.0 ) ) );
        }

        if ( !listPredicates.isEmpty( ) )
        {
            // add existing predicates to Where clause
            query.where( listPredicates.toArray( new Predicate[0] ) );
        }
    }

    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.stock.modules.tickets.business.IShowDAO#getCurrentProduct(java.util.List)
	 */
    /**
     * {@inheritDoc}
     */
    public List<Product> getCurrentProduct( List<String> orderList, PaginationProperties paginator )
	{
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<Product> cq = cb.createQuery( Product.class );

        Root<Product> root = cq.from( Product.class );
        
        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>( );
        
        // date end after current date
        Calendar calendar = new GregorianCalendar( );
        calendar.set( GregorianCalendar.HOUR_OF_DAY, 23 );
        calendar.set( GregorianCalendar.MINUTE, 59 );
        calendar.set( GregorianCalendar.SECOND, 59 );
        Join<Product, ProductAttributeDate> join = root.join( Product_.attributeDateList );
        Join<Product, ProductAttributeDate> join1 = root.join( Product_.attributeDateList );
        listPredicates.add( AttributeDateUtils.between( cb, join, join1, "start", "end",
                new Timestamp( calendar.getTimeInMillis( ) ) ) );

        // add existing predicates to Where clause

        cq.where( listPredicates.toArray( new Predicate[0] ) );
        
        ProductFilter filter = new ProductFilter( );
        filter.setOrderAsc( true );
        filter.setOrders( orderList );
        buildSortQuery( filter, root, cq, cb );
        cq.distinct( true );

        return createPagedQuery( cq, paginator ).getResultList( );
	}

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "deprecation" )
    public List<Product> getComeProduct( List<String> orderList, PaginationProperties paginator )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<Product> cq = cb.createQuery( Product.class );

        Root<Product> root = cq.from( Product.class );

        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        Calendar calendar = new GregorianCalendar( );
        calendar.set( GregorianCalendar.HOUR_OF_DAY, 23 );
        calendar.set( GregorianCalendar.MINUTE, 59 );
        calendar.set( GregorianCalendar.SECOND, 59 );

        Join<Product, ProductAttributeDate> join = root.join( Product_.attributeDateList );
        listPredicates.add( AttributeDateUtils.greaterThan( cb, join, "start",
                new Timestamp( calendar.getTimeInMillis( ) ) ) );

        // add existing predicates to Where clause

        cq.where( listPredicates.toArray( new Predicate[0] ) );

        ProductFilter filter = new ProductFilter( );
        filter.setOrderAsc( true );
        filter.setOrders( orderList );
        buildSortQuery( filter, root, cq, cb );
        cq.distinct( true );

        return createPagedQuery( cq, paginator ).getResultList( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.modules.tickets.business.IShowDAO#
     * getCurrentAndComeProduct(java.util.List)
     */
    /**
     * {@inheritDoc}
     */
    public List<Product> getCurrentAndComeProduct( List<String> orderList )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<Product> cq = cb.createQuery( Product.class );

        Root<Product> root = cq.from( Product.class );

        // predicates list
        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        // date end after current date
        Timestamp currentDate = DateUtils.getCurrentDate( );
        Join<Product, ProductAttributeDate> join = root.join( Product_.attributeDateList );
        listPredicates.add( AttributeDateUtils.greaterThan( cb, join, "end", currentDate ) );

        // add existing predicates to Where clause
        cq.where( listPredicates.toArray( new Predicate[0] ) );

        ProductFilter filter = new ProductFilter( );
        filter.setOrderAsc( true );
        filter.setOrders( orderList );
        buildSortQuery( filter, root, cq, cb );
        cq.distinct( true );

        return createPagedQuery( cq, null ).getResultList( );
    }

    /**
     * Build the sort query.
     * 
     * @param filter the filter
     * @param root the product root
     * @param query the criteria query
     * @param builder the criteria builder
     */
    protected void buildSortQuery( ProductFilter filter, Root<Product> root, CriteriaQuery<Product> query,
            CriteriaBuilder builder )
    {
        if ( filter.getOrders( ) != null && !filter.getOrders( ).isEmpty( ) )
        {
            List<Order> orderList = new ArrayList<Order>( );

            if ( filter.isOrderAsc( ) )
            {
                // get asc order
                for ( String order : filter.getOrders( ) )
                {
                    if ( order.equals( "dateEnd" ) )
                    {
                        Join<Product, ProductAttributeDate> joinProduct = root.join( Product_.attributeDateList );
                        addRestriction( query, builder.equal( joinProduct.get( "key" ), "end" ) );
                        orderList.add( builder.asc( joinProduct.get( "value" ) ) );
                    }
                    else if ( order.equals( "dateStart" ) )
                    {
                        Join<Product, ProductAttributeDate> joinProduct = root.join( Product_.attributeDateList );
                        addRestriction( query, builder.equal( joinProduct.get( "key" ), "start" ) );
                        orderList.add( builder.asc( joinProduct.get( "value" ) ) );
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
                    if ( order.equals( "dateEnd" ) )
                    {
                        Join<Product, ProductAttributeDate> joinProduct = root.join( Product_.attributeDateList );
                        addRestriction( query, builder.equal( joinProduct.get( "key" ), "end" ) );
                        orderList.add( builder.desc( joinProduct.get( "value" ) ) );
                    }
                    else if ( order.equals( "dateStart" ) )
                    {
                        Join<Product, ProductAttributeDate> joinProduct = root.join( Product_.attributeDateList );
                        addRestriction( query, builder.equal( joinProduct.get( "key" ), "start" ) );
                        orderList.add( builder.desc( joinProduct.get( "value" ) ) );
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
