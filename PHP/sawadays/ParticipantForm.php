use Symfony form building tools
use Participant Entity


class ParticipantForm extends AbstractType
{
    public function buildForm(FormBuilder $builder, array $options)
    {
        $builder
            ->add('participants', EntityType::class)
            ->add('save',Submit)
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array('data_class'=>Participant::class,
        'csrf_protection'=>false));
    }

}