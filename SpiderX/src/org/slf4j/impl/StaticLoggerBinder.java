/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * The binding of {@link LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 * 
 * This class is meant to provide a dummy StaticLoggerBinder to the slf4j-api module. 
 * Real implementations are found in  each SLF4J binding project, e.g. slf4j-nop, 
 * slf4j-log4j12 etc.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class StaticLoggerBinder {
 
  /**
   * The unique instance of this class.
   */
  private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
  
  /**
   * Return the singleton of this class.
   * 
   * @return the StaticLoggerBinder singleton
   */
  public static final StaticLoggerBinder getSingleton() {
    return SINGLETON;
  }
  
  /**
   * Declare the version of the SLF4J API this implementation is compiled against. 
   * The value of this field is usually modified with each release. 
   */
  // to avoid constant folding by the compiler, this field must *not* be final
  public static String REQUESTED_API_VERSION = "1.6.99";  // !final
  
  private StaticLoggerBinder() {
    //throw new UnsupportedOperationException("This code should have never made it into slf4j-api.jar");
  }

  public ILoggerFactory getLoggerFactory() {
    return factory;
  }

  public String getLoggerFactoryClassStr() {
	  return "";
  }
  
  ILoggerFactory factory = new ILoggerFactory() {
	
	@Override
	public Logger getLogger(String name) {
		// TODO Auto-generated method stub
		return new Logger() {
			
			@Override
			public void warn(Marker marker, String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(Marker marker, String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(Marker marker, String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(Marker marker, String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(Marker marker, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warn(String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(Marker marker, String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(Marker marker, String format, Object... argArray) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(Marker marker, String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(Marker marker, String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(Marker marker, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isWarnEnabled(Marker marker) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isWarnEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isTraceEnabled(Marker marker) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isTraceEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isInfoEnabled(Marker marker) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isInfoEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isErrorEnabled(Marker marker) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isErrorEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isDebugEnabled(Marker marker) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isDebugEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void info(Marker marker, String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(Marker marker, String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(Marker marker, String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(Marker marker, String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(Marker marker, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void error(Marker marker, String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(Marker marker, String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(Marker marker, String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(Marker marker, String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(Marker marker, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(Marker marker, String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(Marker marker, String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(Marker marker, String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(Marker marker, String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(Marker marker, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(String msg, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(String format, Object... arguments) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(String format, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(String format, Object arg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void debug(String msg) {
				// TODO Auto-generated method stub
				
			}
		};
	}
};
}
