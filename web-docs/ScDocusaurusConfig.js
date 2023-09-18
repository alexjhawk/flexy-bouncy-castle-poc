// CONFIGURATION ZONE - YOU CAN EDIT THESE LINES
const ScDocusaurusConfig = {
  repoOwnerName: 'alexjhawk',
  repoName: 'flexy-bouncy-castle-poc',
  title: 'Ewon Flexy Bouncy Castle Proof of Concept Project',
  description: 'A proof of concept implementation of the Bouncy Castle library for the Ewon Flexy.',
  meta: 'Homepage for the Ewon Flexy Bouncy Castle Proof of Concept Project.',
};

// EXPORT ZONE - DON'T TOUCH BELOW THIS LINE
module.exports = {
  ...ScDocusaurusConfig,
  repoUrl: 'https://github.com/' + ScDocusaurusConfig.repoOwnerName + '/' + ScDocusaurusConfig.repoName,
  repoArchiveUrl: 'https://github.com/' + ScDocusaurusConfig.repoOwnerName + '/' + ScDocusaurusConfig.repoName + '/archive/refs/heads/main.zip',
  repoLatestReleaseUrl: 'https://github.com/' + ScDocusaurusConfig.repoOwnerName + '/' + ScDocusaurusConfig.repoName + '/releases/latest',
  repoNewIssueUrl: 'https://github.com/' + ScDocusaurusConfig.repoOwnerName + '/' + ScDocusaurusConfig.repoName + '/issues/new',
};