package org.elasticsearch.plugin.swift;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.repositories.RepositoriesModule;
import org.elasticsearch.repositories.swift.SwiftRepository;
import org.elasticsearch.repositories.swift.SwiftRepositoryModule;
import org.elasticsearch.repositories.swift.SwiftService;

import java.util.Collection;

/**
 * Our base plugin stuff.
 */
public class SwiftRepositoryPlugin extends AbstractPlugin {
	// Elasticsearch settings
    private final Settings settings;

    /**
     * Constructor. Sets settings to settings.
     * @param settings Our settings
     */
    public SwiftRepositoryPlugin(Settings settings) {
        this.settings = settings;
    }

    /**
     * Plugin name, duh.
     */
    @Override
    public String name() {
        return "swift-repository";
    }

    /**
     * A description. Also duh.
     */
    @Override
    public String description() {
        return "Swift repository plugin";
    }

    /**
     * Register our modules, if needed.
     */
    @Override
    public Collection<Class<? extends Module>> modules() {
        Collection<Class<? extends Module>> modules = Lists.newArrayList();
        if (settings.getAsBoolean("swift.repository.enabled", true)) {
            modules.add(SwiftModule.class);
        }
        return modules;
    }

    /**
     * Register our services, if needed.
     */
	@Override
	@SuppressWarnings("rawtypes")
	public Collection<Class<? extends LifecycleComponent>> services() {
        Collection<Class<? extends LifecycleComponent>> services = Lists.newArrayList();
        if (settings.getAsBoolean("swift.repository.enabled", true)) {
            services.add(SwiftService.class);
        }
        return services;
    }

	/**
	 * Load our repository module into the list, if enabled
	 * @param repositoriesModule The repositories module to register ourselves with
	 */
    public void onModule(RepositoriesModule repositoriesModule) {
        if (settings.getAsBoolean("swift.repository.enabled", true)) {
            repositoriesModule.registerRepository(SwiftRepository.TYPE, SwiftRepositoryModule.class);
        }
    }
}
