import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
  title: string;
  Svg: React.ComponentType<React.ComponentProps<'svg'>>;
  description: JSX.Element;
};

const FeatureList: FeatureItem[] = [
  {
    title: 'Support for multiple AI models',
    Svg: require('@site/static/img/undraw_docusaurus_mountain.svg').default,
    description: (
      <>
          The plugin supports multiple AI models, including Xunfei Spark, Aliyun Qwen, to
          understand and generate high-quality multilingual text
      </>
    ),
  },
  {
    title: 'Integration with Docusaurus',
    Svg: require('@site/static/img/undraw_docusaurus_tree.svg').default,
    description: (
      <>
          The plugin is designed to work seamlessly with Docusaurus, a popular open-source
          static site generator for documentation websites. Ensure that the translated document is compiled and presented
          correctly.
      </>
    ),
  },
  {
    title: 'Open Source',
    Svg: require('@site/static/img/undraw_docusaurus_react.svg').default,
    description: (
      <>
          maven-translate4markdown-plugin is 100% open source and available on <a href="https://github.com/paion-data/maven-translation4markdown-plugin">Github</a>. Released
          under the commercial-friendly
          <a href="http://www.apache.org/licenses/LICENSE-2.0.html"> Apache License, Version 2.0.</a>
      </>
    ),
  },
];

function Feature({title, Svg, description}: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} role="img" />
      </div>
      <div className="text--center padding-horiz--md">
        <Heading as="h3">{title}</Heading>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): JSX.Element {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
