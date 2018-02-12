/*
 * The MIT License
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., Alan Harder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.plugins.sidebar_link;

import hudson.model.Action;
import hudson.util.FormValidation;
import java.util.List;
import javax.annotation.CheckForNull;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Simple link.
 * @author Alan Harder
 */
public class LinkAction implements Action {
    private final String url, text;
    
    /**
     * Caches the fact that the URL is safe or not.
     * It assumes that {@link LinkProtection#ALLOWED_URI_SCHEMES} is assigned once and only once on the startup.
     */
    @CheckForNull
    private transient Boolean isSafe = null;

    @DataBoundConstructor
    public LinkAction(String urlName, String displayName ) throws IllegalArgumentException {
        // Validate URL before proceeding
        FormValidation validationResult = LinkProtection.verifyUrl(urlName);
        if(validationResult.kind == FormValidation.Kind.ERROR) {
            throw new IllegalArgumentException(validationResult);
        }

        this.url = urlName;
	    this.text = displayName;
    }

    public String getUrlName() {
        if (isSafe == null) { // Refresh cache
            FormValidation validationResult = LinkProtection.verifyUrl(url);
            isSafe = validationResult.kind != FormValidation.Kind.ERROR;
        }
        return isSafe ? url : "unsafeLink-" + url.hashCode();
    }
    
    public String getDisplayName() { return text; }
    public String getIconFileName() { return "new-computer.png"; }
    public String getJavascript() { return "\"<script type=\"text/javascript\" src=\"${rootURL}/plugin/server-status-plugin/Script.js\"></script>\""; }

    @Restricted(NoExternalUse.class)
    public String getUnprotectedUrlName() {
        return url;
    }
    
    @Restricted(NoExternalUse.class)
    public Object getAncestor() {
        StaplerRequest currentRequest = Stapler.getCurrentRequest();
        if (currentRequest != null) {
            List<Ancestor> ancestors = currentRequest.getAncestors();
            int listSize = ancestors != null? ancestors.size() : 0;
            if (ancestors == null || listSize < 2) {
                return null;
            }
            // One level above the LinkAction
            return ancestors.get(listSize - 2).getObject();
        }
        
        return null;
    }
    
    @Restricted(NoExternalUse.class)
    public String getAllowedSchemes() {
        return LinkProtection.getAllowedUriSchemes();
    }
}
