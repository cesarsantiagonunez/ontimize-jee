//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2017.09.05 at 03:04:01 PM CEST
//

package com.ontimize.jee.common.util.serializer.xml;

import com.ontimize.jee.common.db.SQLStatementBuilder.BasicExpression;
import com.ontimize.jee.common.gui.SearchValue;
import com.ontimize.jee.common.util.serializer.xml.XmlFilterQuery.Filters.Filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * Java class for xmlFilterQuery complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="xmlFilterQuery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="filter" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                             &lt;element name="basic-expression" type="{}xmlFilterBasicExpression" minOccurs="0"/>
 *                             &lt;element name="search-value" type="{}xmlFilterSearchValue" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmlFilterQuery", propOrder = { "filters" })
@XmlRootElement(name = "query-filters")
public class XmlFilterQuery {

    @XmlElement(required = true)
    protected XmlFilterQuery.Filters filters;

    /**
     * Gets the value of the filters property.
     * @return possible object is {@link XmlFilterQuery.Filters }
     *
     */
    public XmlFilterQuery.Filters getFilters() {
        return this.filters;
    }

    /**
     * Sets the value of the filters property.
     * @param value allowed object is {@link XmlFilterQuery.Filters }
     *
     */
    public void setFilters(XmlFilterQuery.Filters value) {
        this.filters = value;
    }

    public void setFiltersMap(Map<String, Object> filter) {
        this.setFilters(new Filters());
        for (Entry<String, Object> actualEntry : filter.entrySet()) {
            Object actualValue = actualEntry.getValue();
            this.filters.getFilter().add(new Filter(actualEntry.getKey(), actualValue));
        }
    }

    public Map<String, Object> getFiltersMap() {
        HashMap<String, Object> toRet = new HashMap<String, Object>();

        for (Filter actualFilter : this.getFilters().getFilter()) {

            toRet.put(actualFilter.getKey(), actualFilter.getFilterValue());
        }

        return toRet;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="filter" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                   &lt;element name="basic-expression" type="{}xmlFilterBasicExpression" minOccurs="0"/>
     *                   &lt;element name="search-value" type="{}xmlFilterSearchValue" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    /*
     * @XmlAccessorType(XmlAccessType.FIELD)
     *
     * @XmlType(name = "", propOrder = { "filter" })
     *
     */
    public static class Filters {

        protected List<XmlFilterQuery.Filters.Filter> filter;

        /**
         * Gets the value of the filter property.
         *
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any
         * modification you make to the returned list will be present inside the JAXB object. This is why
         * there is not a <CODE>set</CODE> method for the filter property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getFilter().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list {@link XmlFilterQuery.Filters.Filter }
         *
         *
         */
        public List<XmlFilterQuery.Filters.Filter> getFilter() {
            if (this.filter == null) {
                this.filter = new ArrayList<XmlFilterQuery.Filters.Filter>();
            }
            return this.filter;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         *
         * <p>
         * The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *         &lt;element name="basic-expression" type="{}xmlFilterBasicExpression" minOccurs="0"/>
         *         &lt;element name="search-value" type="{}xmlFilterSearchValue" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "key", "value", "basicExpression", "searchValue" })
        public static class Filter {

            @XmlElement(required = true)
            protected String key;

            protected Object value;

            @XmlElement(name = "basic-expression")
            protected XmlFilterBasicExpression basicExpression;

            @XmlElement(name = "search-value")
            protected XmlFilterSearchValue searchValue;

            public Filter() {
                // TODO Auto-generated constructor stub
            }

            public Filter(String key, Object value) {
                this.key = key;
                if (value instanceof SearchValue) {
                    this.searchValue = new XmlFilterSearchValue((SearchValue) value);
                } else if (value instanceof BasicExpression) {
                    this.basicExpression = new XmlFilterBasicExpression((BasicExpression) value);
                } else {
                    this.value = value;
                }
            }

            public Object getFilterValue() {
                if ((this.value != null) && (this.basicExpression == null) && (this.searchValue == null)) {
                    return this.getValue();
                } else if ((this.value == null) && (this.basicExpression != null) && (this.searchValue == null)) {
                    return this.basicExpression.getBasicExpression();
                } else if ((this.value == null) && (this.basicExpression == null) && (this.searchValue != null)) {
                    return this.searchValue.getSearchValue();
                }
                return null;
            }

            /**
             * Gets the value of the key property.
             * @return possible object is {@link String }
             *
             */
            public String getKey() {
                return this.key;
            }

            /**
             * Sets the value of the key property.
             * @param value allowed object is {@link String }
             *
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * @return possible object is {@link Object }
             *
             */
            public Object getValue() {
                return this.value;
            }

            /**
             * Sets the value of the value property.
             * @param value allowed object is {@link Object }
             *
             */
            public void setValue(Object value) {
                this.value = value;
            }

            /**
             * Gets the value of the basicExpression property.
             * @return possible object is {@link XmlFilterBasicExpression }
             *
             */
            public XmlFilterBasicExpression getBasicExpression() {
                return this.basicExpression;
            }

            /**
             * Sets the value of the basicExpression property.
             * @param value allowed object is {@link XmlFilterBasicExpression }
             *
             */
            public void setBasicExpression(XmlFilterBasicExpression value) {
                this.basicExpression = value;
            }

            /**
             * Gets the value of the searchValue property.
             * @return possible object is {@link XmlFilterSearchValue }
             *
             */
            public XmlFilterSearchValue getSearchValue() {
                return this.searchValue;
            }

            /**
             * Sets the value of the searchValue property.
             * @param value allowed object is {@link XmlFilterSearchValue }
             *
             */
            public void setSearchValue(XmlFilterSearchValue value) {
                this.searchValue = value;
            }

        }

    }

}
