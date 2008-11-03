/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.xnio.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.jboss.xnio.IoUtils;

/**
 * TODO - maybe change to cache the selector
 */
public final class SelectorUtils {
    private SelectorUtils() {
    }

    public static void await(int op, SelectableChannel channel) throws IOException {
        final Selector selector = Selector.open();
        try {
            channel.register(selector, op);
            selector.select();
        } finally {
            IoUtils.safeClose(selector);
        }
    }

    public static void await(int op, SelectableChannel channel, long time, TimeUnit unit) throws IOException {
        final Selector selector = Selector.open();
        try {
            channel.register(selector, op);
            selector.select(unit.toMillis(time));
        } finally {
            IoUtils.safeClose(selector);
        }
    }
}