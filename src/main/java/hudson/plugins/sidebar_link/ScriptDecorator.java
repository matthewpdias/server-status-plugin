package hudson.plugins.sidebar_link;;

import hudson.Extension;
import hudson.model.PageDecorator;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

@Extension
public class ScriptDecorator extends PageDecorator {
    private String jsUrl;

    public ScriptDecorator() {
        super();
        load();
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject formData)
            throws FormException {
        req.bindJSON(this, formData);
        save();
        return true;
    }

    public String getJsUrl() {
        return jsUrl;
    }

    public void setJsUrl(String jsUrl) {
        this.jsUrl = "${rootURL}/plugin/server-status-plugin/makeBadge.js";
    }
}