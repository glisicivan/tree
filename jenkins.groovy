def item = Jenkins.instance.getItem('Pipelines')
println item.getItems()
//println item.getItemDescriptors()[0].getDisplayName()
//println item.getItemDescriptors()[8].getDisplayName()
		if (item == null)
			throw new Exception("Plan with name Release PoC doesn't exist")
		def lastSuccessfulBuild
		if (item instanceof org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject) {
			item.items.each { build ->
				println(build.displayName)
				if (build.displayName.equalsIgnoreCase("release/2018.5_jen")) {
					lastSuccessfulBuild = build.getLastSuccessfulBuild()
				}
			}
		}else {
			lastSuccessfulBuild = item.getLastSuccessfulBuild()
		}
		def actions = lastSuccessfulBuild.getActions()
		def sha = 'development'
		actions.each { action ->
			if (action instanceof hudson.plugins.git.util.BuildData && action.getRemoteUrls().contains("ssh://git@leo-stash.internal.sungard.corp:7999/sglem/live.git")) {
				sha = action.getLastBuiltRevision().getSha1String()
			}
		}
		println sha
