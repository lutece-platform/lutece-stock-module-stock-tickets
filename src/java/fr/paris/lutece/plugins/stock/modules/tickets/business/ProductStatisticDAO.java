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

import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.product.Product_;
import fr.paris.lutece.plugins.stock.commons.dao.AbstractStockDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.service.TicketsPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * This class provides Data Access methods for Person objects
 */
public final class ProductStatisticDAO extends AbstractStockDAO<Integer, ProductStatistic> implements IProductStatisticDAO
{
    private static final String SELECT_ALL_RESULT_STATISTIC_BY_MARAMETERS = "SELECT count(distinct product_statistic.product_id_product) AS compteur, product_statistic.";
    private static final String SELECT_ALL_RESULT_STATISTIC_BY_PARAMETERS_END = ",product_statistic.year FROM stock_ticket_product_statistic AS product_statistic";
    private static final String SELECT_ALL_COUNT_PRODUCTS_BY_DATES = "SELECT count( distinct product_statistic.product_id_product) FROM stock_ticket_product_statistic AS product_statistic";
    private static final String WHERE_CLAUSE = " WHERE";
    private static final String AND_OPERATOR = " AND";
    private static final String WEEK_PARAMETER = "week";
    private static final String MONTH_PARAMETER = "month";
    private static final String DAY_OF_YEAR_PARAMETER = "dayOfYear";
    private static final String GROUP_BY_PRODUCT_STATISTIC = " GROUP BY product_statistic.";
    private static final String WHERE_PRODUCT_STATISTIC_DATE = " WHERE product_statistic.date >= CAST('";
    private static final String PRODUCT_STATISTIC_DATE = " product_statistic.date <= CAST('";
    private static final String END_OF_DAY = " 23:59:59' AS DATETIME)";
    private static final String START_OF_DAY = " 00:00:00' AS DATETIME)";
    private static final String PRODUCT_STAITSTIC_YEAR = ", product_statistic.year";
    
    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getPluginName( )
    {
        return TicketsPlugin.PLUGIN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public List<ProductStatistic> getAllByIdProduct( Integer idProduct )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );

        CriteriaQuery<ProductStatistic> cq = cb.createQuery( ProductStatistic.class );

        Root<ProductStatistic> root = cq.from( ProductStatistic.class );

        // predicates list
        List<Predicate> listPredicates = new ArrayList<>( );

        Join<ProductStatistic, Product> product = root.join( ProductStatistic_.product, JoinType.INNER );

        if ( idProduct != null )
        {
            listPredicates.add( cb.equal( product.get( Product_.id ), idProduct ) );
        }

        if ( !listPredicates.isEmpty( ) )
        {
            // add existing predicates to Where clause
            cq.where( listPredicates.toArray( new Predicate [ 0] ) );
        }

        cq.distinct( true );

        TypedQuery<ProductStatistic> query = em.createQuery( cq );

        return query.getResultList( );
    }

    /**
     * {@inheritDoc}
     */
    public List<ResultStatistic> getAllResultStatisticByParameters( String strTimesUnit, String strDateDebut, String strDateFin, Plugin plugin )
    {
        StringBuilder requeteSQL = new StringBuilder( );

        requeteSQL.append( SELECT_ALL_RESULT_STATISTIC_BY_MARAMETERS );

        if ( strTimesUnit.equals( "0" ) )
        {
            requeteSQL.append( DAY_OF_YEAR_PARAMETER );
        }
        else
            if ( strTimesUnit.equals( "1" ) )
            {
                requeteSQL.append( WEEK_PARAMETER );
            }
            else
            {
                requeteSQL.append( MONTH_PARAMETER );
            }

        requeteSQL.append( SELECT_ALL_RESULT_STATISTIC_BY_PARAMETERS_END );

        boolean isFirstCondition = Boolean.TRUE;

        if ( ( strDateDebut != null ) && !strDateDebut.equals( "" ) )
        {
            requeteSQL.append( WHERE_PRODUCT_STATISTIC_DATE + strDateDebut + START_OF_DAY );
            isFirstCondition = Boolean.FALSE;
        }

        if ( ( strDateFin != null ) && !strDateFin.equals( "" ) )
        {
            if ( isFirstCondition )
            {
                requeteSQL.append( WHERE_CLAUSE );
            }
            else
            {
                requeteSQL.append( AND_OPERATOR );
            }

            requeteSQL.append( PRODUCT_STATISTIC_DATE + strDateFin + END_OF_DAY );
        }

        requeteSQL.append( GROUP_BY_PRODUCT_STATISTIC );

        if ( strTimesUnit.equals( "0" ) )
        {
            requeteSQL.append( DAY_OF_YEAR_PARAMETER );
        }
        else
            if ( strTimesUnit.equals( "1" ) )
            {
                requeteSQL.append( WEEK_PARAMETER );
            }
            else
            {
                requeteSQL.append( MONTH_PARAMETER );
            }

        requeteSQL.append( PRODUCT_STAITSTIC_YEAR );
        
        List<ResultStatistic> resultsStatistics = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( requeteSQL.toString( ), plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                ResultStatistic resultStatistic = new ResultStatistic( );
                int nIndex = 1;

                resultStatistic.setNumberResponse( daoUtil.getInt( nIndex++ ) );
                resultStatistic.setStatisticDate( daoUtil.getTimestamp( nIndex++ ) );

                resultsStatistics.add( resultStatistic );
            }
        }  
            return resultsStatistics;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountProductsByDates( String strDateDebut, String strDateFin, Plugin plugin )
    {
        Integer countProducts = 0;
        StringBuilder requeteSQL = new StringBuilder( );

        requeteSQL.append( SELECT_ALL_COUNT_PRODUCTS_BY_DATES );

        boolean isFirstCondition = Boolean.TRUE;

        if ( ( strDateDebut != null ) && !strDateDebut.equals( "" ) )
        {
            requeteSQL.append( WHERE_PRODUCT_STATISTIC_DATE + strDateDebut + START_OF_DAY );
            isFirstCondition = Boolean.FALSE;
        }

        if ( ( strDateFin != null ) && !strDateFin.equals( "" ) )
        {
            if ( isFirstCondition )
            {
                requeteSQL.append( WHERE_CLAUSE );
            }
            else
            {
                requeteSQL.append( AND_OPERATOR );
            }

            requeteSQL.append( PRODUCT_STATISTIC_DATE + strDateFin + END_OF_DAY );
        }

        try ( DAOUtil daoUtil = new DAOUtil( requeteSQL.toString( ), plugin ) )
        {
            daoUtil.executeQuery( );
            int nIndex = 0;
            
            while ( daoUtil.next( ) )
            {
                countProducts = daoUtil.getInt( nIndex++);
            }
        }
        return countProducts;
    }

    @Override
    public List<ResultStatistic> getAllResultStatisticByParameters(String strTimesUnit, String strDateDebut,
            String strDateFin)
    {
        return new ArrayList<>( );
    }

    @Override
    public Integer getCountProductsByDates(String strDateDebut, String strDateFin)
    {
        return null;
    }
}
