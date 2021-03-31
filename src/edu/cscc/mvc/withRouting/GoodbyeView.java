package edu.cscc.mvc.withRouting;

import edu.cscc.mvc.withRouting.framework.ApplicationView;
import edu.cscc.mvc.withRouting.framework.MVCContext;

public class GoodbyeView extends ApplicationView {
    /**
     * Sets the context and configures it to exit by default
     * unless a route is specified.
     *
     * @param context The {@link MVCContext}.
     */
    public GoodbyeView(MVCContext context) {
        super(context);
    }

    @Override
    public void show() {
        System.out.println("See ya later!");

    }
}
