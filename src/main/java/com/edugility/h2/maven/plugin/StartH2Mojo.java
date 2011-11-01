/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil -*-
 *
 * Copyright (c) 2011-2011 Edugility LLC.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * The original copy of this license is available at
 * http://www.opensource.org/license/mit-license.html.
 */
package com.edugility.h2.maven.plugin;

import java.sql.SQLException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.plugin.logging.Log;

import org.h2.tools.Server;

/**
 * @goal start
 */
public class StartH2Mojo extends AbstractH2Mojo {

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    final Log log = this.getLog();
    Server server = this.getServer();
    if (server == null) {
      try {
        server = this.createServer();
      } catch (final SQLException kaboom) {
        throw new MojoExecutionException("Could not create the H2 server.", kaboom);
      }
      if (log != null && log.isDebugEnabled()) {
        log.debug(String.format("Created H2 server with the following arguments: %s", this.getServerArguments()));
      }
    }
    this.setServer(server);
    try {
      server.start();
    } catch (final SQLException kaboom) {
      throw new MojoExecutionException("Could not start the H2 server.", kaboom);
    }
    if (log != null && log.isInfoEnabled()) {
      log.info("H2 server started: " + server.getStatus());
    }
  }

}